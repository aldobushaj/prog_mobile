package com.example.bnext;

import static com.example.bnext.MainActivity.url;

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
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import model.User;

public class SignUp extends AppCompatActivity {

    TextView textSignUp, infoTextSignUp, emailText2, passwordText2;
    EditText inputEmail2, inputPassword2;
    Button buttonSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AndroidNetworking.initialize(getApplicationContext());

        textSignUp = findViewById(R.id.textSignUp);
        infoTextSignUp = findViewById(R.id.infoTextSignUp);
        emailText2 = findViewById(R.id.emailText2);
        passwordText2 = findViewById(R.id.passwordText2);
        inputEmail2 = findViewById(R.id.inputEmail2);
        inputPassword2 = findViewById(R.id.inputPassword2);
        buttonSignUp = findViewById(R.id.buttonSignUp);


        buttonSignUp.setOnClickListener(view -> {

            User user = new User(inputEmail2.getText().toString(), inputPassword2.getText().toString());
            AndroidNetworking.post(url+"user/signup")
                    //negli header per il token fare sempre così .addHeaders("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9")
                    .addApplicationJsonBody(user)
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            Log.println(Log.INFO, "signUp result", "Registrazione effettuata con successo!");

                            Log.println(Log.INFO, "Request is: ", user.toString());
                            //og.println(Log.INFO,"User: ", user.toString());

                            // il login ha avuto successo, vado alla prossima pagina
                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onError(ANError error) {
                            // Qua cosa   fare se la richiesta va in errore
                            Toast.makeText(view.getContext(), "Something went wrong with Sign Up", Toast.LENGTH_SHORT).show();
                            System.out.println(error);
                        }

                    });

        });
    }
}