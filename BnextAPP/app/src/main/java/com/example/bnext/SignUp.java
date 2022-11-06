package com.example.bnext;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    TextView textSignUp, infoTextSignUp, emailText2, passwordText2;
    EditText inputEmail2, inputPassword2;
    Button buttonSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textSignUp = findViewById(R.id.textSignUp);
        infoTextSignUp = findViewById(R.id.infoTextSignUp);
        emailText2 = findViewById(R.id.emailText2);
        passwordText2 = findViewById(R.id.passwordText2);
        inputEmail2 = findViewById(R.id.inputEmail2);
        inputPassword2 = findViewById(R.id.inputPassword2);
        buttonSignUp = findViewById(R.id.buttonSignUp);

    }
}