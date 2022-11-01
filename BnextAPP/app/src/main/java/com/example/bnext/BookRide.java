package com.example.bnext;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.User;

public class BookRide extends AppCompatActivity {
    String token = "";
    Button SearchRideButton, BookRideButton;
    TextView UserNameTextView;
    EditText PriceKmEditText, DestinationEditText, DateChooseEditText, TimeChooseEditText;
    ListView AvailableCarsListView;
    ImageView userAvatar;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ride);

        // Recupero il token ottenuto dalla pagina di login
        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");
        Log.d("Token BookRide",token);
        // Recupero lo user ottenuto dalla pagina di login
        currentUser = (User) getIntent().getSerializableExtra("currentUser");
        Log.d("User BookRide", currentUser.toString());

        // ---- assign values to each control of the layout----
        //Buttons
        SearchRideButton = findViewById(R.id.SearchRideButton);
        BookRideButton = findViewById(R.id.bookRideButton);

        // Text Views
        UserNameTextView = findViewById(R.id.UserNameTextView);
        UserNameTextView.setText(currentUser.getName() + " " + currentUser.getSurname() +
                "\n " + currentUser.getUsername());

        // Edit Text

        PriceKmEditText = findViewById(R.id.PriceKmEditText);
        DestinationEditText = findViewById(R.id.DestinationEditText);
        DateChooseEditText = findViewById(R.id.DateChooseEditText);
        TimeChooseEditText = findViewById(R.id.TimeChooseEditText);

        // List View
        AvailableCarsListView = findViewById(R.id.AvaiableCarsListView);


        // Image Buttons
        userAvatar = findViewById(R.id.userAvatar);


        //click listener rimpiazzato con una lambda
        SearchRideButton.setOnClickListener(view -> {
            AndroidNetworking.initialize(getApplicationContext());
            // pipedream https://eo36hxzz25l7d4r.m.pipedream.net
            /*Example request as string JSON parsable
            * {
                 "startOfBook" : "2020-07-27T13:31:00",
                  "endOfBook": "2020-07-27T14:10:00"
               }
            * */
            //String payload ="{ 'startOfBook:' "+ DateChooseEditText.getText()
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("startOfBook",  DateChooseEditText.getText());
                jsonObject.put("endOfBook", DateChooseEditText.getText());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //localhost:8080/reservations/availableCars
            // https://eo36hxzz25l7d4r.m.pipedream.net
            AndroidNetworking.post("http://10.0.2.2:8080/reservations/availableCars")
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
                            Log.d("Updated user", currentUser.toString());
                            Toast.makeText(BookRide.this, s, Toast.LENGTH_LONG).show();
                            // Così ritorniamo alla home dopo l'update

                        }

                        @Override
                        public void onError(ANError anError) {
                            // Qua cosa   fare se la richiesta va in errore
                            Toast.makeText(BookRide.this, anError.getErrorDetail(), Toast.LENGTH_LONG).show();
                            System.out.println(anError);
                        }
                    });

        });




        userAvatar.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), UserDetailsActivity.class);
            intent.putExtra("token", token);
            intent.putExtra("currentUser",currentUser);
            view.getContext().startActivity(intent);

        });

        BookRideButton.setOnClickListener(view -> {
            /* Per ora va direttamente,
            dopo va fatto che si procede a quella dopo
            selezionando una macchina e passando all'activity
            seguente le informazioni
            * */
            Intent intent = new Intent(view.getContext(), BookActivity.class);
            // Quindi posso passare il token di autenticazione all'altra activity
            intent.putExtra("token", token);
            intent.putExtra("currentUser",currentUser);
            view.getContext().startActivity(intent);

        });





    }
}