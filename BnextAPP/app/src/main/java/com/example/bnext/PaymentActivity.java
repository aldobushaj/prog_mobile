package com.example.bnext;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import model.Car;
import model.User;

public class PaymentActivity extends AppCompatActivity {

    TextView totalText;

    Button endTripButton, feedbackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Car currentCar=  (Car) getIntent().getSerializableExtra("currentCar");
        User currentUser=  (User) getIntent().getSerializableExtra("currentUser");

        totalText = findViewById(R.id.textReservation);
        endTripButton = findViewById(R.id.endTripButton);
        feedbackButton = findViewById(R.id.feedbackButton);



        //currentCar.getReservation()
        //totalText.setText(currentCar.getReservation().listIterator().next().getStartOfBook().toString());




        feedbackButton.setOnClickListener(view -> {
            /* Per ora va direttamente,
            dopo va fatto che si procede a quella dopo
            selezionando una macchina e passando all'activity
            seguente le informazioni
            * */
            Intent intent = new Intent(view.getContext(), AddComment.class);
            // Quindi posso passare il token di autenticazione all'altra activity
            //intent.putExtra("token", token);
            intent.putExtra("currentUser",currentUser);
            intent.putExtra("currentCar",currentCar);
            view.getContext().startActivity(intent);

        });

    }
}