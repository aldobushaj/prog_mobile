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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    // Token ottenuto dal login
    String token = "";

    TextView signInText, infoText, infoText2, infoText3, emailText, passwordText;
    EditText inputEmail, inputPassword;
    Button loginButton, facebookButton, signUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // assign values to each control of the layout

        signInText = findViewById(R.id.signInText);
        infoText = findViewById(R.id.infoText);
        infoText2 = findViewById(R.id.infoText2);
        infoText3 = findViewById(R.id.infoText3);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        loginButton = findViewById(R.id.loginButton);
        facebookButton = findViewById(R.id.facebookButton);
        signUpButton = findViewById(R.id.signUpButton);



        //click listeners
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputEmail.length() != 0 && inputPassword.length() != 0) {
                    DataService dataService = new DataService(MainActivity.this);


                    dataService.login(inputEmail.getText().toString(),inputPassword.getText().toString(), new DataService.VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(MainActivity.this, "Something went wrong with Log In", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(JSONObject response) {
                            // memorizzo il token una volta fatta l'autenticazione, per usarlo nello prossime richieste
                            try {
                                token = (String) response.get("token");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(MainActivity.this, "Value of data : " + response, Toast.LENGTH_SHORT).show();

                            Log.println(Log.INFO, "signIn result", "Success");

                            // il login ha avuto successo, vado alla prossima pagina
                            Intent intent = new Intent(MainActivity.this, BookRide.class);

                            // Quindi posso passare il token di autenticazione all'altra activity
                            intent.putExtra("token", token);

                            //Create the bundle
                            /*Bundle b = new Bundle();
                            //Add your data to bundle
                            b.putString("token", token);
                            intent.putExtras(b);*/

                            startActivity(intent);
                        }
                    });


                }else{
                    Log.println(Log.INFO, "signIn result", "You must enter username and password to login");
                }
            }
        });



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });




        /*btn_getWeatherByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataService  dataService = new DataService(MainActivity.this);

                dataService.getCar(token, new DataService.ArrayResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message , Toast.LENGTH_SHORT).show();
                    }

                   @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(MainActivity.this,"Value of data : " + response, Toast.LENGTH_SHORT).show();
                    }
                });

                //Log.println(Log.INFO, "signIn result", "bitcoin");
                //Toast.makeText(MainActivity.this,"Value of data : " + bitcoin, Toast.LENGTH_SHORT).show();

            }
            /*@Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You clicked me 2", Toast.LENGTH_SHORT).show();
            }*/
        /*});







        btn_getWeatherByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, StatsCarActivity.class);
                startActivity(intent);
                /*Toast.makeText(
                        MainActivity.this,
                        "You typed " + et_dataInput.getText().toString(),
                        Toast.LENGTH_SHORT).show();*/
           /* }
        });*/
    }
}