package com.example.bnext;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import model.Car;
import model.Feedback;
import model.User;

public class AddComment extends AppCompatActivity {

    // ---- token will be sent from PaymentActivity----
    String token ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcm92YSIsImlhdCI6MTY2Nzc1MTM3OSwiZXhwIjoxNjY3NzY5Mzc5fQ.JpSoP74LAP3CWYweJ__177tYVCVzb1bHQmwwbr6MMP15uZgjDm8rTkhz718K-39h01QXdZ_mK_z0N5rOuMyrGQ";


    TextView carName, carPlateNumber, userToComment;
    EditText feedbackSection;
    ImageView carImage, userImage;
    Button addFeedback;
    User currentUser;
    String url = "http://10.0.2.2:8080/";
    String carID,userID;
    ArrayList<Feedback> feedbacks = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        // ---- assign values to each control of the layout----
        carName = findViewById(R.id.carName);
        carPlateNumber = findViewById(R.id.carPlateNumber);
        userToComment = findViewById(R.id.userToComment);
        feedbackSection = findViewById(R.id.feedbackSection);
        carImage = findViewById(R.id.carImage);
        userImage = findViewById(R.id.userImage);
        addFeedback = findViewById(R.id.addFeedback);

        // modify text
        carName.setText("Tesla Model Y");
        carPlateNumber.setText("Plate number: ABC123");

        // assign text to a feedback

        // Populate the UI with Fast Android Networking Library
        AndroidNetworking.initialize(getApplicationContext());
        currentUser = (User) getIntent().getSerializableExtra("currentUser");


        addFeedback.setOnClickListener(view -> {
            JSONObject jsonObject = new JSONObject();
            Car car = new Car(UUID.fromString("991f9af1-bed7-4911-b46c-6ac88080e046"));
            User user = new User(UUID.fromString("579d6ff5-487b-43eb-9cb8-3279703c41cd"),"prova");
            Feedback feedbackFromTextView= new Feedback(feedbackSection.getText().toString(), user,car);

            try {
                jsonObject.put("comment",feedbackSection.getText().toString());
                jsonObject.put("user",new JSONObject(new Gson().toJson(user)));
                jsonObject.put("car",new JSONObject(new Gson().toJson(car)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("**************\n"+jsonObject);
            AndroidNetworking.post("http://10.0.2.2:8080/feedbacks/addFeed")
                    //negli header per il token fare sempre così .addHeaders("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9")
                    .addHeaders("Authorization", "Bearer " + token)
                    .addHeaders("accept", "*/*")
                    .addHeaders("accept-encoding", "gzip, deflate, br")
                    //.addHeaders("content-type","application/json")
                    //.setContentType("application/json; charset=utf-8")
                    //.addApplicationJsonBody(DateChooseEditText.getText())
                    .addJSONObjectBody(jsonObject) // posting json
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String s) {
                            // Qua cosa   fare se la richiesta funziona
                            //Log.d("Updated user", currentUser.toString());
                            Toast.makeText(AddComment.this, "Comment added SUCESFULLY!", Toast.LENGTH_SHORT).show();
                            // Ripulisco l'edit text contenete il feedback appena aggiunto
                            feedbackSection.setText("");
                            // Così ritorniamo alla home dopo l'update

                        }

                        @Override
                        public void onError(ANError anError) {
                            // Qua cosa   fare se la richiesta va in errore
                            //Toast.makeText(BookRide.this, anError.getErrorDetail(), Toast.LENGTH_LONG).show();
                            System.out.println(anError);
                        }
                    });


        });


    }
}