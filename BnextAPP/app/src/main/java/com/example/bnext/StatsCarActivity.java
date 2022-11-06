package com.example.bnext;



import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import model.Car;
import model.Feedback;
import model.User;

public class StatsCarActivity extends AppCompatActivity {

    String token ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcm92YSIsImlhdCI6MTY2NzcyOTIyNiwiZXhwIjoxNjY3NzQ3MjI2fQ.2klve4VNBmAgVmx6SbT1p0m0qIujqYysMMvlUeiaDGqvPYrNaYHeBrtOX7ccxxzLfxoeAlyv05jm8T7zFyNTmg";

    String url = "http://10.0.2.2:8080/";
    String carID,userID;
    TextView carName, carPlateNumber, priceHour, priceKM;
    Button bookRideButton;
    ListView commentSection;
    ImageView carImage;
    // ArrayList che contiene la lista di oggetti Feedback appartenenti ad una macchina (macchina passata da un altra activity)
    final ArrayList<Feedback> feedbacks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_car);
        setTitle("Stats car");

        // Recupero il token ottenuto dalla pagina di login
        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");
        Car currentCar=  (Car) getIntent().getSerializableExtra("currentCar");

        // ---- assign values to each control of the layout----
        carName = findViewById(R.id.carName);
        carPlateNumber = findViewById(R.id.carPlateNumber);
        priceHour = findViewById(R.id.priceHour);
        priceKM = findViewById(R.id.priceKM);
        bookRideButton = findViewById(R.id.bookRideButton);
        commentSection = findViewById(R.id.commentSection);
        carImage = findViewById(R.id.carImage);

        // modify text
        carName.setText(currentCar.getName());
        carPlateNumber.setText("Plate number: " + currentCar.getPlateNumber());
        priceKM.append("5€");
        priceHour.append("50€");


        // ------------------------------------- DA SISTEMARE --------------------------------------------------
        // questo prenderà il valore che passiamo dall'activity Home/BookRide sia per l'user che per la macchina
        //carID = "991f9af1-bed7-4911-b46c-6ac88080e046";
        carID = String.valueOf(currentCar.getCarId());
        //userID= String.valueOf(currentCar.getUser().getUserId());
        // -----------------------------------------------------------------------------------------------------

        // Populate the UI with Fast Android Networking Library
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.initialize(getApplicationContext());
        // get the list of objects of type Feedback
        AndroidNetworking.get(url+ "feedbacks/"+carID)
                //negli header per il token fare sempre così .addHeaders("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9")
                .addHeaders("Authorization", "Bearer " + token)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    // Qua cosa   fare se la richiesta funziona
                    public void onResponse(JSONArray response) {

                        for (int i=0; i< response.length(); i++) {
                            try {
                                // recupero l'oggetto
                                JSONObject resp = (JSONObject) response.get(i);

                                Gson gson  = new GsonBuilder()
                                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                        .create();
                                //System.out.println("###############\n"+response.get(i));
                                //Car car = new Car(UUID.fromString(carID));
                                //User user = new User(UUID.fromString(userID),resp.get("nomeUtente").toString(), resp.get("cognomeUtente").toString());
                                //Feedback feed = new Feedback(UUID.fromString(resp.get("idFeedback").toString()), resp.get("comment").toString(), car, user);
                                Feedback feed = gson.fromJson(resp.toString(), Feedback.class); // deserializes json into target2
                                feedbacks.add(feed);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //System.out.println("Finito");
                        Log.println(Log.INFO,"Feedbacks: ", feedbacks.toString());

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        // Qua cosa   fare se la richiesta va in errore
                        Toast.makeText(StatsCarActivity.this, error.getErrorCode(), Toast.LENGTH_LONG).show();
                        System.out.println(error);
                    }

                });


        // Now create the instance of the CustomViewAdapter and pass
        // the context and arrayList created above
        CustomViewAdapter customViewAdapter = new CustomViewAdapter(StatsCarActivity.this, feedbacks);

        // set the CustomViewAdapter for ListView
        commentSection.setAdapter(customViewAdapter);


        //System.out.println("*************************ççççççççççç"+feedbacks);


        bookRideButton.setOnClickListener(view -> {

            Intent intent = new Intent(StatsCarActivity.this, BookActivity.class);
            startActivity(intent);
        });

        //carName.setTypeface(null, Typeface.BOLD_ITALIC);
        priceHour.setTypeface(null, Typeface.BOLD);
        priceKM.setTypeface(null, Typeface.BOLD);

        //carName.setTypeface(null, Typeface.ITALIC);
        //carName.setTypeface(null, Typeface.NORMAL);*/
    }
}