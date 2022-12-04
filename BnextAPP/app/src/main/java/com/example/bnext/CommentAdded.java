package com.example.bnext;
import static com.example.bnext.MainActivity.url;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import model.Car;
import model.Feedback;
import model.User;

public class CommentAdded extends AppCompatActivity {
    TextView carName, carPlateNumber, userToComment;
    EditText feedbackSection;
    ImageView carImage, userImage;
    Button addFeedback, home;
    User currentUser;
    Car currentCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_added);
        currentCar=  (Car) getIntent().getSerializableExtra("currentCar");
        currentUser=  (User) getIntent().getSerializableExtra("currentUser");

        // ---- assign values to each control of the layout----
        carName = findViewById(R.id.carName);
        carPlateNumber = findViewById(R.id.carPlateNumber);
        userToComment = findViewById(R.id.userToComment);
        carImage = findViewById(R.id.carImage);
        addFeedback = findViewById(R.id.addFeedback);
        home = findViewById(R.id.home);


        // set image of car
        String src = currentCar.getName().toLowerCase()+"_"+currentCar.getCarModel().toLowerCase();
        int drawableId = this.getResources().getIdentifier(src, "drawable", this.getPackageName());
        carImage.setImageResource(drawableId);

        // modify text
        carName.setText(currentCar.getName());
        carPlateNumber.setText("Plate number: "+currentCar.getPlateNumber());

        // assign text to a feedback

        // Populate the UI with Fast Android Networking Library
        AndroidNetworking.initialize(getApplicationContext());

        addFeedback.setOnClickListener(view -> {
            //Car car = new Car(UUID.fromString("1566ee1b-1668-4815-8c2b-aeebd169b13b"));
            //User user = new User(UUID.fromString("2e34e2e0-c9b0-4ebd-83cb-2cffde2b333d"),"prova");


            Intent intent = new Intent(view.getContext(), AddComment.class);
            intent.putExtra("currentUser",currentUser);
            intent.putExtra("currentCar",currentCar);
            view.getContext().startActivity(intent);
        });

        home.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), BookRide.class);
            intent.putExtra("currentUser",currentUser);
            intent.putExtra("currentCar",currentCar);
            view.getContext().startActivity(intent);
        });


    }
}