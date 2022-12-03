package com.example.bnext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.Locale;

import model.Car;
import model.User;

public class PaymentActivity extends AppCompatActivity {

    TextView titlePayament,resumePayament,startBooking, endBooking, dateStartPayment,
            dateEndPayment, hourStartPayment, hourEndPayment,totalText;
    Button feedbackButton;
    ImageView successImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Car currentCar=  (Car) getIntent().getSerializableExtra("currentCar");
        User currentUser=  (User) getIntent().getSerializableExtra("currentUser");
        String startOfBook = (String) getIntent().getSerializableExtra("startOfBook");
        String endOfBook= (String) getIntent().getSerializableExtra("endOfBook");


        titlePayament = findViewById(R.id.titlePayament);
        successImage = findViewById(R.id.successImage);
        resumePayament = findViewById(R.id.resumePayament);
        startBooking = findViewById(R.id.startBooking);
        endBooking = findViewById(R.id.endBooking);
        dateStartPayment = findViewById(R.id.dateStartPayment);
        dateEndPayment = findViewById(R.id.dateEndPayment);
        hourStartPayment = findViewById(R.id.hourStartPayment);
        hourEndPayment = findViewById(R.id.hourEndPayment);
        totalText = findViewById(R.id.totalText);
        feedbackButton = findViewById(R.id.feedbackButton);

        String dateStart = startOfBook.split("T")[0];
        // substring la uso per eliminare i secondi e il separatore :
        String hourStart = startOfBook.split("T")[1].substring(0, startOfBook.split("T")[1].length() - 3);
        String dateEnd = endOfBook.split("T")[0];
        // substring la uso per eliminare i secondi e il separatore :
        String hourEnd = endOfBook.split("T")[1].substring(0, endOfBook.split("T")[1].length() - 3);

        // setto opportunamente le date e gli orari
        dateStartPayment.setText(dateStart);
        hourStartPayment.setText(hourStart);
        dateEndPayment.setText(dateEnd);
        hourEndPayment.setText(hourEnd);
        totalText.append((currentCar.getPriceHour() * getNumberOfHours(startOfBook,endOfBook)) +"€");

        //currentCar.getReservation()
        //totalText.setText(currentCar.getReservation().listIterator().next().getStartOfBook().toString());



        // vado all'activity dei feedback
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

    private long getNumberOfHours(String endOfBook, String startOfBook){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        Date startDate = null, endDate = null;

        try {
            startDate = format.parse(startOfBook);
            endDate = format.parse(endOfBook);
        } catch (ParseException e) {
            Log.e("Error parsing date", startOfBook + " " + endOfBook);
            e.printStackTrace();
        }

        // calcolo il numero di ore tra le due date
        long numberOfHours = startDate.getTime() - endDate.getTime();

        return numberOfHours;
    }
}