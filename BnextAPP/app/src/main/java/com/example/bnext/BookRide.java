package com.example.bnext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    ImageView userAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ride);

        // Recupero il token ottenuto dalla pagina di login
        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");
        //System.out.println("Ecco il token\n******** "+token);



        // ---- assign values to each control of the layout----
        //Buttons
        SearchRideButton = findViewById(R.id.SearchRideButton);
        BookRideButton = findViewById(R.id.bookRideButton);

        // Text Views
        UserNameTextView = findViewById(R.id.UserNameTextView);

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


        userAvatar.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), UserDetailsActivity.class);
            view.getContext().startActivity(intent);

        });

        BookRideButton.setOnClickListener(view -> {
            /* Per ora va direttamente,
            dopo va fatto che si procede a quella dopo
            selezionando una macchina e passando all'activity
            seguente le informazioni
            * */
            Intent intent = new Intent(view.getContext(), BookActivity.class);
            view.getContext().startActivity(intent);

        });





    }
}