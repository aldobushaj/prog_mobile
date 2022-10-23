package com.example.bnext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import model.User;

public class UserDetailsActivity extends AppCompatActivity {
    String token = "";
    Button HomeUserDetailsButton, BookRideButton;
    TextView UserNameTextView;
    EditText UsernameEditText, EmailEditText;
    ListView AvailableCarsListView;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        // ---- assign values to each control of the layout----
        //Buttons
        HomeUserDetailsButton = findViewById(R.id.homeUserDetailsButton);
        // Edit Text
        UsernameEditText = findViewById(R.id.usernameEditText);
        EmailEditText = findViewById(R.id.emailEditText);
        UserNameTextView = findViewById(R.id.userNameTextView);
        // Populate the UI with Fast Android Networking Library
        AndroidNetworking.initialize(getApplicationContext());
        //http://10.0.2.2:8080/user/allUsers
        // qua mettiamo l'url della richiesta sempre con http://10.0.2.2:8080/ per il localhost
        AndroidNetworking.get("http://10.0.2.2:8080/user/allUsers")
                //negli header per il token fare sempre così .addHeaders("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9")
                .addHeaders("Authorization", "Bearer " + token)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Qua cosa   fare se la richiesta funziona
                        try {
                            //Deserializzo il JSON in un oggetto User
                            Gson gson = new Gson(); // Or use new GsonBuilder().create();
                            currentUser = gson.fromJson(response.get(0).toString(), User.class); // deserializes json into target2
                            for (int i = 0; i < response.length(); ++i) {


                                JSONObject jsn = response.getJSONObject(i);
                                UserNameTextView.setText(
                                        currentUser.getName()
                                                + " "
                                                + currentUser.getSurname()
                                                + "\n Roles: "
                                                + currentUser.getRoles()

                                );
                                UsernameEditText.setText(jsn.get("username").toString());
                                Log.d("Get response", currentUser.toString());

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(UserDetailsActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        // Qua cosa   fare se la richiesta va in errore
                        Toast.makeText(UserDetailsActivity.this, error.getErrorCode(), Toast.LENGTH_LONG).show();
                        System.out.println(error);
                    }
                });

        HomeUserDetailsButton.setOnClickListener(view -> {
            AndroidNetworking.initialize(getApplicationContext());
            AndroidNetworking.post("http://10.0.2.2:8080/user/update")
                    //negli header per il token fare sempre così .addHeaders("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9")
                    .addHeaders("Authorization", "Bearer " + token)
                    .addBodyParameter("userId", currentUser.getUserId().toString())
                    .addBodyParameter("username", UsernameEditText.getText().toString())
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // Qua cosa   fare se la richiesta funziona


                            Log.d("Updated user", currentUser.toString());
                            Toast.makeText(UserDetailsActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                            // Così ritorniamo alla home dopo l'update
                            Intent intent = new Intent(view.getContext(), BookRide.class);
                            view.getContext().startActivity(intent);
                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error
                            // Qua cosa   fare se la richiesta va in errore
                            Toast.makeText(UserDetailsActivity.this, error.getErrorCode(), Toast.LENGTH_LONG).show();
                            System.out.println(error);
                        }

                    });


        });

    }
}