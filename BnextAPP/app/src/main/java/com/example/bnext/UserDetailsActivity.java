package com.example.bnext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.net.CronetProviderInstaller;

import org.chromium.net.CronetEngine;

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


        HomeUserDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Così ritorniamo alla home quando clicco sul bottone
                Intent intent = new Intent(view.getContext(), BookRide.class);
                view.getContext().startActivity(intent);
            }
        });

    }
}