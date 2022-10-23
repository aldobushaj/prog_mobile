package com.example.bnext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import model.User;

public class UserDetailsActivity extends AppCompatActivity {
    String token = "";
    Button UpdateUserDetailsButton, BookRideButton;
    TextView UserNameTextView;
    EditText UsernameEditText, EmailEditText;
    ListView AvailableCarsListView;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        // Recupero il token ottenuto dalla pagina di login
        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");
        // ---- assign values to each control of the layout----
        //Buttons
        UpdateUserDetailsButton = findViewById(R.id.homeUserDetailsButton);
        // Edit Text
        UsernameEditText = findViewById(R.id.usernameEditText);
        EmailEditText = findViewById(R.id.emailEditText);
        UserNameTextView = findViewById(R.id.userNameTextView);
        currentUser = (User) getIntent().getSerializableExtra("currentUser");
        Log.d("User BookRide", currentUser.toString());

        UserNameTextView.setText(
                currentUser.getName()
                        + " "
                        + currentUser.getSurname()
                        + "\n Roles: "
                        + currentUser.getRoles()

        );
        UsernameEditText.setText(currentUser.getUsername());

        UpdateUserDetailsButton.setOnClickListener(view -> {

            User updatedUser = new User(currentUser.getUserId(), UsernameEditText.getText().toString());
            AndroidNetworking.initialize(getApplicationContext());
            // pipedream https://eo36hxzz25l7d4r.m.pipedream.net
            AndroidNetworking.put("http://10.0.2.2:8080/user/update")
                    //negli header per il token fare sempre così .addHeaders("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9")
                    .addHeaders("Authorization", "Bearer " + token)
                    .addHeaders("accept", "*/*")
                    .addHeaders("accept-encoding", "gzip, deflate, br")
                    //.addHeaders("content-type","application/json")
                    //.setContentType("application/json; charset=utf-8")
                    .addApplicationJsonBody(updatedUser)
                    //.addJSONObjectBody(jsonObject) // posting json
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String s) {
                            // Qua cosa   fare se la richiesta funziona
                            Log.d("Updated user", currentUser.toString());
                            Toast.makeText(UserDetailsActivity.this, s, Toast.LENGTH_LONG).show();
                            // Così ritorniamo alla home dopo l'update
                            Intent intent = new Intent(view.getContext(), MainActivity.class);

                            view.getContext().startActivity(intent);

                        }

                        @Override
                        public void onError(ANError anError) {
                            // Qua cosa   fare se la richiesta va in errore
                            Toast.makeText(UserDetailsActivity.this, anError.getErrorDetail(), Toast.LENGTH_LONG).show();
                            System.out.println(anError);
                        }
                    });

        });

    }
}

/*
*  // Populate the UI with Fast Android Networking Library
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
*
* */