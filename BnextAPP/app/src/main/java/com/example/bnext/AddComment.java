package com.example.bnext;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddComment extends AppCompatActivity {


    TextView carName, carPlateNumber, userToComment, feedbackSection;
    ImageView carImage, userImage;
    Button addFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        carName = findViewById(R.id.carName);
        carPlateNumber = findViewById(R.id.carPlateNumber);
        userToComment = findViewById(R.id.userToComment);
        feedbackSection = findViewById(R.id.feedbackSection);
        carImage = findViewById(R.id.carImage);
        userImage = findViewById(R.id.userImage);
        addFeedback = findViewById(R.id.addFeedback);

    }
}