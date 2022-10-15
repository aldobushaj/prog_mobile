package com.example.bnext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {

    TextView totalText;

    Button endTripButton, feedbackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        totalText = findViewById(R.id.totalText);
        endTripButton = findViewById(R.id.endTripButton);
        feedbackButton = findViewById(R.id.feedbackButton);

    }
}