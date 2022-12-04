package com.example.bnext;

import static android.text.TextUtils.isEmpty;
import static com.example.bnext.MainActivity.url;

import android.annotation.SuppressLint;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.User;

public class SignUp extends AppCompatActivity {

    EditText name, surname, username, password;
    Button buttonSignUp, alredyRegitered;
    TextInputEditText birthdate;

    User currentUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        birthdate = findViewById(R.id.birthdate);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        alredyRegitered = findViewById(R.id.alredyRegitered);

        // Populate the UI with Fast Android Networking Library
        AndroidNetworking.initialize(getApplicationContext());

        buttonSignUp.setOnClickListener(view -> {
            Toast.makeText(this, "messaggio di prova", Toast.LENGTH_SHORT);
            /*if (isEmpty(name.getText())) {
                Toast t = Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT);
                t.show();
            }
            if (isEmpty(surname.getText())) {
                Toast t = Toast.makeText(this, "You must enter surname to register!", Toast.LENGTH_SHORT);
                t.show();
            }
            if (isEmpty(username.getText())) {
                Toast t = Toast.makeText(this, "You must enter username to register!", Toast.LENGTH_SHORT);
                t.show();
            }
            if (isEmpty(password.getText())) {
                Toast t = Toast.makeText(this, "You must enter password to register!", Toast.LENGTH_SHORT);
                t.show();
            }
            if (isEmpty(birthdate.getText())) {
                Toast t = Toast.makeText(this, "You must enter birthdate to register!", Toast.LENGTH_SHORT);
                t.show();
            }*/

            if (name.length() != 0 && surname.length() != 0 && username.length() != 0
                    && password.length() != 0 ) {
                //Date date = (Date)birthdate.getText();
                //DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                //String strDate = dateFormat.format(date);
                //2020-12-31T10:00:00.000+00:00
                User user = new User(name.getText().toString(), surname.getText().toString(),birthdate.getText().toString(), username.getText().toString(),
                        password.getText().toString());
                //User user = new User(inputEmail2.getText().toString(), inputPassword2.getText().toString());
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

            }else{
                Toast.makeText(SignUp.this, "You must enter username and password to login", Toast.LENGTH_SHORT).show();
                Log.println(Log.INFO, "signIn result", "You must enter username and password to login");
            }
        });

        alredyRegitered.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.putExtra("currentUser",currentUser);
            startActivity(intent);
        });



    }
}