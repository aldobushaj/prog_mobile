package com.example.bnext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import model.User;

public class MainActivity extends AppCompatActivity {
    // Token ottenuto dal login
    static String token = "";
    static String url = "http://10.0.2.2:8080/";

    TextView signInText, infoText, infoText2, infoText3, emailText, passwordText;
    EditText inputEmail, inputPassword;
    Button loginButton, facebookButton, signUpButton;

    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // assign values to each control of the layout

        signInText = findViewById(R.id.signInText);
        infoText = findViewById(R.id.infoText);
        //infoText2 = findViewById(R.id.infoText2);
        infoText3 = findViewById(R.id.infoText3);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        loginButton = findViewById(R.id.loginButton);
        //facebookButton = findViewById(R.id.facebookButton);
        signUpButton = findViewById(R.id.signUpButton);

        // Populate the UI with Fast Android Networking Library
        AndroidNetworking.initialize(getApplicationContext());


        loginButton.setOnClickListener(view -> {
            if (inputEmail.length() != 0 && inputPassword.length() != 0) {

                final User[] user = {new User(inputEmail.getText().toString(), inputPassword.getText().toString())};
                AndroidNetworking.initialize(getApplicationContext());
                AndroidNetworking.post(url+"user/signin")
                        //negli header per il token fare sempre così .addHeaders("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9")
                        .addApplicationJsonBody(user[0])
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.println(Log.INFO, "signIn result", "Inizio");


                                // Qua cosa   fare se la richiesta funziona

                                try {
                                    token = (String) response.get("token");
                                    Gson gson  = new GsonBuilder()
                                            //.setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                            //.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                                            .create();
                                    currentUser = gson.fromJson(response.get("user").toString(), User.class); // deserializes json into target2
                                    //System.out.println("####################################\n"+currentUser.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.println(Log.INFO, "SignIn result", "Success");
                                //og.println(Log.INFO,"User: ", user.toString());

                                // il login ha avuto successo, vado alla prossima pagina
                                Intent intent = new Intent(MainActivity.this, BookRide.class);

                                // Quindi posso passare il token di autenticazione all'altra activity
                                intent.putExtra("currentUser",currentUser);
                                //intent.putExtra("token",  token);

                                //Create the bundle
                            /*Bundle b = new Bundle();
                            //Add your data to bundle
                            b.putString("token", token);
                            intent.putExtras(b);*/

                                startActivity(intent);
                            }

                            @Override
                            public void onError(ANError error) {
                                // Qua cosa   fare se la richiesta va in errore
                                Toast.makeText(MainActivity.this, "Something went wrong with Log In", Toast.LENGTH_SHORT).show();
                                System.out.println(error);
                            }

                        });

            }else{
                Toast.makeText(MainActivity.this, "You must enter username and password to login", Toast.LENGTH_SHORT).show();
                Log.println(Log.INFO, "signIn result", "You must enter username and password to login");
            }
        });




        signUpButton.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        });

    }
}