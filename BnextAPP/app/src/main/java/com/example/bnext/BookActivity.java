package com.example.bnext;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import model.Car;
import model.User;

public class BookActivity extends AppCompatActivity {

    TextView textReservation, oreText, priceText,nameOfCar;
    Button startBookButton;

    final Calendar myCalendar= Calendar.getInstance();
    EditText timeText, dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        /*
        Car currentCar=  (Car) getIntent().getSerializableExtra("currentCar");
        User currentUser=  (User) getIntent().getSerializableExtra("currentUser");*/
        Car currentCar= new Car(UUID.fromString("32ca6698-6525-4fba-bf6f-7a30dfb2500d"));
        User currentUser = new User(UUID.fromString("28ba6606-93de-4931-a81b-eb8708f10e78"));

        textReservation = findViewById(R.id.textReservation);
        oreText = findViewById(R.id.oreText);
        priceText = findViewById(R.id.priceText);
        startBookButton = findViewById(R.id.feedbackButton);
        dateText= findViewById(R.id.dateStart);
        timeText= findViewById(R.id.hourStart);
        nameOfCar = findViewById(R.id.nameOfCar);

        getSupportActionBar().setTitle("Reservation");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //nameOfCar.setTypeface(null,Typeface.BOLD);
        //textReservation.setTypeface(Typeface.DEFAULT_BOLD);
        //oreText.setText("Il veicolo sta arrivando...");
        //priceText.setText("Price / Hour: ");
        //priceText.append(currentCar.getPriceHour().toString()+ " €");





        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabelDate();
        };

        TimePickerDialog.OnTimeSetListener time = (view,hour, minute ) -> {
            myCalendar.set(Calendar.HOUR_OF_DAY, hour);
            myCalendar.set(Calendar.MINUTE,minute);
            updateLabelTime();
        };
        //Toast.makeText(this, myCalendar.get(Calendar.HOUR_OF_DAY), Toast.LENGTH_SHORT).show();


        timeText.setOnClickListener(view -> new TimePickerDialog(this, time,
                myCalendar.get(Calendar.HOUR_OF_DAY),myCalendar.get(Calendar.MINUTE), true).show());


        dateText.setOnClickListener(view -> new DatePickerDialog(this, date,
                myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());




        //editText.setOnClickListener(this::showDatePickerDialog);


        startBookButton.setOnClickListener(view -> {
            System.out.println("<<<<<<>>>>>>>>>>>>>>>> "+timeText.getText());
            System.out.println("<<<<<<>>>>>>>>>>>>>>>> "+dateText.getText());

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



    private void updateLabelDate(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        dateText.setText(dateFormat.format(myCalendar.getTime()));
    }
    private void updateLabelTime()  {
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.US);
        timeText.setText(dateFormat.format(myCalendar.getTime()));
    }


}