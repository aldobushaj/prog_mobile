package com.example.bnext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.android.gms.net.CronetProviderInstaller;

import org.chromium.net.CronetEngine;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserDetailsActivity extends AppCompatActivity {
    String token = "";
    Button HomeUserDetailsButton, BookRideButton;
    TextView UserNameTextView;
    EditText UsernameEditText,EmailEditText;
    ListView AvailableCarsListView;

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
        UserNameTextView= findViewById(R.id.userNameTextView);
        // Populate the UI with Fast Android Networking Library
        AndroidNetworking.initialize(getApplicationContext());
        //http://10.0.2.2:8080/user/allUsers
        // qua mettiamo l'url della richiesta sempre con http://10.0.2.2:8080/ per il localhost
        AndroidNetworking.get("http://10.0.2.2:8080/user/allUsers")
                //negli header per il token fare sempre così .addHeaders("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9")
                .addHeaders("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcm92YSIsImlhdCI6MTY2NjUwOTY3MiwiZXhwIjoxNjY2NTI3NjcyfQ.uZylo6iClbKnffjyX9i6G1VPjYuDx7JN90Llh3mRWChaXXmEKTOW94OeHE_-r759P6x8AXH69R40_cKmD7pqYQ")
                .addPathParameter("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcm92YSIsImlhdCI6MTY2NjUwOTY3MiwiZXhwIjoxNjY2NTI3NjcyfQ.uZylo6iClbKnffjyX9i6G1VPjYuDx7JN90Llh3mRWChaXXmEKTOW94OeHE_-r759P6x8AXH69R40_cKmD7pqYQ")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Qua cosa   fare se la richiesta funziona
                        try {
                            for (int i = 0; i < response.length(); ++i) {

                                JSONObject jsn = response.getJSONObject(i);
                                UserNameTextView.setText(
                                        jsn.get("name").toString()
                                        + " "
                                        + jsn.get("surname").toString()
                                        + "\n Roles: "
                                        + jsn.get("roles").toString()

                                );
                                UsernameEditText.setText(jsn.get("username").toString());
                                Log.d("Get response",jsn.get("name").toString());

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(UserDetailsActivity.this,response.toString() , Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        // Qua cosa   fare se la richiesta va in errore
                        Toast.makeText(UserDetailsActivity.this,error.getErrorCode(), Toast.LENGTH_LONG).show();
                        System.out.println(error.toString());
                    }
                });

        HomeUserDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            /*
                OkHttpClient client = new OkHttpClient();
                String url = "http://10.0.2.2:8080/user/allUsers";

                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    try (Response response = client.newCall(request).execute()) {
                        System.out.println( response.body().string());
                    }
                    catch (Exception e){
                        System.out.println(e.getStackTrace());

                    }

         */





                // Così ritorniamo alla home quando clicco sul bottone HOME
                //Intent intent = new Intent(view.getContext(), BookRide.class);
                //view.getContext().startActivity(intent);
            }
        });

    }
}