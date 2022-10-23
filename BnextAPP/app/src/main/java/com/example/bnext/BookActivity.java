package com.example.bnext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;

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


    }
}