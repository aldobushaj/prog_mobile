package com.example.bnext;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import model.Car;
import model.User;

public class BookActivity extends AppCompatActivity {

    TextView infoText, oreText, priceText;
    Button startBookButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);


        infoText = findViewById(R.id.totalText);
        oreText = findViewById(R.id.oreText);
        priceText = findViewById(R.id.priceText);
        startBookButton = findViewById(R.id.feedbackButton);

        priceText.setText("Price / Hour: 12........");


        Car currentCar=  (Car) getIntent().getSerializableExtra("currentCar");
        User currentUser=  (User) getIntent().getSerializableExtra("user");


        System.out.println("################\n"+currentUser+"\n"+currentCar);

    }
}