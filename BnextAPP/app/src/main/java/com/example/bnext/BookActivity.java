package com.example.bnext;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import model.Car;
import model.User;

public class BookActivity extends AppCompatActivity {

    TextView infoText, oreText, priceText;
    Button startBookButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Car currentCar=  (Car) getIntent().getSerializableExtra("currentCar");
        User currentUser=  (User) getIntent().getSerializableExtra("currentUser");

        infoText = findViewById(R.id.totalText);
        oreText = findViewById(R.id.oreText);
        priceText = findViewById(R.id.priceText);
        startBookButton = findViewById(R.id.feedbackButton);


        oreText.setText("Il veicolo sta arrivando...");
        priceText.setText("Price / Hour: ");
        priceText.append(currentCar.getPriceHour().toString()+ " €");


        startBookButton.setOnClickListener(view -> {
            /* Per ora va direttamente,
            dopo va fatto che si procede a quella dopo
            selezionando una macchina e passando all'activity
            seguente le informazioni
            * */
            Intent intent = new Intent(view.getContext(), PaymentActivity.class);
            // Quindi posso passare il token di autenticazione all'altra activity
            //intent.putExtra("token", token);
            intent.putExtra("currentUser",currentUser);
            intent.putExtra("currentCar",currentCar);
            view.getContext().startActivity(intent);

        });
    }
}