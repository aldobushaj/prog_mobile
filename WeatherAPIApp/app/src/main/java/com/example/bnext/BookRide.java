package com.example.bnext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class BookRide extends AppCompatActivity {
    String token = "";
    Button SearchRideButton, BookRideButton;
    TextView UserNameTextView;
    EditText PriceKmEditText, DestinationEditText, DateChooseEditText, TimeChooseEditText;
    ListView AvailableCarsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ride);

        // ---- assign values to each control of the layout----
        //Buttons
        SearchRideButton = findViewById(R.id.SearchRideButton);
        BookRideButton = findViewById(R.id.BookRideButton);

        // Text Views
        UserNameTextView = findViewById(R.id.UserNameTextView);

        // Edit Text

        PriceKmEditText = findViewById(R.id.PriceKmEditText);
        DestinationEditText = findViewById(R.id.DestinationEditText);
        DateChooseEditText = findViewById(R.id.DateChooseEditText);
        TimeChooseEditText = findViewById(R.id.TimeChooseEditText);

        // List View
        AvailableCarsListView = findViewById(R.id.AvaiableCarsListView);



        //click listener rimpiazzato con una lambda
        SearchRideButton.setOnClickListener(view -> {

            //Creo il dataService e passo questa activity come context
            DataService  dataService = new DataService(BookRide.this);
            /*
            dataService.signIn(new DataService.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(BookRide.this,"Something wrong BookRide Search  " , Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(JSONObject response) {
                    // memorizzo il token una volta fatta l'autenticazione, per usarlo nello prossime richieste
                    try {
                        token= (String) response.get("token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this,"Value of data : " + response, Toast.LENGTH_SHORT).show();
                }
            });
            Log.println(Log.INFO, "signIn result", "bitcoin");
            //Toast.makeText(MainActivity.this,"Value of data : " + bitcoin, Toast.LENGTH_SHORT).show();
        */
        });



    }
}