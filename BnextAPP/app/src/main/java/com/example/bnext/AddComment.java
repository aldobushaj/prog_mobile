package com.example.bnext;
import static com.example.bnext.MainActivity.token;
import static com.example.bnext.MainActivity.url;

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

    TextView carName, carPlateNumber, userToComment;
    EditText feedbackSection;
    ImageView carImage, userImage;
    Button addFeedback;
    User currentUser;
    Car currentCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        currentCar=  (Car) getIntent().getSerializableExtra("currentCar");
        currentUser=  (User) getIntent().getSerializableExtra("currentUser");
        // ---- assign values to each control of the layout----
        carName = findViewById(R.id.carName);
        carPlateNumber = findViewById(R.id.carPlateNumber);
        userToComment = findViewById(R.id.userToComment);
        feedbackSection = findViewById(R.id.feedbackSection);
        carImage = findViewById(R.id.carImage);
        userImage = findViewById(R.id.userImage);
        addFeedback = findViewById(R.id.addFeedback);


        // set image of car
        String src = currentCar.getName().toLowerCase()+"_"+currentCar.getCarModel().toLowerCase();
        int drawableId = this.getResources().getIdentifier(src, "drawable", this.getPackageName());
        carImage.setImageResource(drawableId);

        // modify text
        carName.setText(currentCar.getName());
        carPlateNumber.setText(currentCar.getPlateNumber());
        userToComment.append(" "+currentUser.getUsername());
        // assign text to a feedback

        // Populate the UI with Fast Android Networking Library
        AndroidNetworking.initialize(getApplicationContext());



        addFeedback.setOnClickListener(view -> {
            JSONObject jsonObject = new JSONObject();
            //Car car = new Car(UUID.fromString("1566ee1b-1668-4815-8c2b-aeebd169b13b"));
            //User user = new User(UUID.fromString("2e34e2e0-c9b0-4ebd-83cb-2cffde2b333d"),"prova");
            Feedback feedbackFromTextView= new Feedback(feedbackSection.getText().toString(), currentUser,currentCar);

            try {
                jsonObject.put("comment",feedbackSection.getText().toString());
                jsonObject.put("user",new JSONObject(new Gson().toJson(currentUser)));
                jsonObject.put("car",new JSONObject(new Gson().toJson(currentCar)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("**************\n"+jsonObject);
            AndroidNetworking.post(url+"feedbacks/addFeed")
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
                            // Ripulisco l'edit text contenete il feedback appena aggiunto
                            feedbackSection.setText("");

                            //vado nella pagia che mi conferma l'aggiunta del commento
                            Intent intent = new Intent(view.getContext(), CommentAdded.class);
                            intent.putExtra("currentUser",currentUser);
                            intent.putExtra("currentCar",currentCar);
                            view.getContext().startActivity(intent);
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