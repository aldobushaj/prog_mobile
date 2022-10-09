package com.example.bnext;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class StatsCarActivity extends AppCompatActivity {

    TextView carName, carPlateNumber, priceHour, priceKM;
    Button bookRideButton;
    ListView commentSection;
    ImageView carImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_car);
        setTitle("Stats car");

        // ---- assign values to each control of the layout----
        carName = findViewById(R.id.carName);
        carPlateNumber = findViewById(R.id.carPlateNumber);
        priceHour = findViewById(R.id.priceHour);
        priceKM = findViewById(R.id.priceKM);
        bookRideButton = findViewById(R.id.bookRideButton);
        commentSection = findViewById(R.id.commentSection);
        carImage = findViewById(R.id.carImage);


        // modify text
        carName.setText("Tesla Model Y");
        carPlateNumber.setText("Plate number: ABC123");


        //carName.setTypeface(null, Typeface.BOLD_ITALIC);
        //carName.setTypeface(null, Typeface.BOLD);
        //carName.setTypeface(null, Typeface.ITALIC);
        //carName.setTypeface(null, Typeface.NORMAL);
    }
}