package com.example.bnext;

import static com.example.bnext.MainActivity.token;
import static com.example.bnext.MainActivity.url;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import model.Car;
import model.Feedback;
import model.Position;
import model.User;

public class BookActivity extends AppCompatActivity {

    TextView nameOfCar, priceText, oreText, textReservation, startReservation, endReservation;
    EditText hourStart, dateStart, hourEnd, dateEnd;
    Button bookButton;
    final Calendar myCalendar= Calendar.getInstance();
    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcm92YSIsImlhdCI6MTY3MDA2MzIzOSwiZXhwIjoxNjcwMDgxMjM5fQ.nNCP0MkNMjKvHlce2Qvqc4nSB7hPtd6JMYPtJalqB0Ezsrm__xfE1dfKY15OFgdh_O88kx41O4hfxz8RMypRWw";
    String myFormat="yyyy/MM/dd";
    Car currentCar;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        /*
        Car currentCar=  (Car) getIntent().getSerializableExtra("currentCar");
        User currentUser=  (User) getIntent().getSerializableExtra("currentUser");*/

        currentCar= new Car(UUID.fromString("19658da5-f573-4c7d-931d-15cd80dc676d"));
        currentUser = new User(UUID.fromString("67f65c6a-4627-4449-b4f5-beb14f0c4139"));

        nameOfCar = findViewById(R.id.nameOfCar);
        priceText = findViewById(R.id.priceText);
        oreText = findViewById(R.id.oreText);
        textReservation = findViewById(R.id.textReservation);
        startReservation= findViewById(R.id.startReservation);
        endReservation= findViewById(R.id.endReservation);
        dateStart = findViewById(R.id.dateStart);
        hourStart = findViewById(R.id.hourStart);
        dateEnd = findViewById(R.id.dateEnd);
        hourEnd = findViewById(R.id.hourEnd);
        bookButton = findViewById(R.id.bookButton);

        getSupportActionBar().setTitle("Reservation");

        AndroidNetworking.initialize(getApplicationContext());

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //nameOfCar.setTypeface(null,Typeface.BOLD);
        //textReservation.setTypeface(Typeface.DEFAULT_BOLD);
        //oreText.setText("Il veicolo sta arrivando...");
        //priceText.setText("Price / Hour: ");
        //priceText.append(currentCar.getPriceHour().toString()+ " €");





        DatePickerDialog.OnDateSetListener start_date = (view, year, month, day) -> {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabelStartDate();
        };

        TimePickerDialog.OnTimeSetListener start_hour = (view,hour, minute ) -> {
            myCalendar.set(Calendar.HOUR_OF_DAY, hour);
            myCalendar.set(Calendar.MINUTE,minute);
            updateLabelStartTime();
        };

        DatePickerDialog.OnDateSetListener end_date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabelEndDate();
        };

        TimePickerDialog.OnTimeSetListener end_hour = (view,hour, minute ) -> {
            myCalendar.set(Calendar.HOUR_OF_DAY, hour);
            myCalendar.set(Calendar.MINUTE,minute);
            updateLabelEndTime();
        };
        //Toast.makeText(this, myCalendar.get(Calendar.HOUR_OF_DAY), Toast.LENGTH_SHORT).show();


        hourStart.setOnClickListener(view -> new TimePickerDialog(this, start_hour,
                myCalendar.get(Calendar.HOUR_OF_DAY),myCalendar.get(Calendar.MINUTE), true).show());


        dateStart.setOnClickListener(view -> new DatePickerDialog(this, start_date,
                myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        hourEnd.setOnClickListener(view-> new TimePickerDialog(this, end_hour,
                myCalendar.get(Calendar.HOUR_OF_DAY),myCalendar.get(Calendar.MINUTE), true).show());

        dateEnd.setOnClickListener(view -> new DatePickerDialog(this, end_date,
                myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());


        //editText.setOnClickListener(this::showDatePickerDialog);


        bookButton.setOnClickListener(view -> {
            handlingErrors();
            // verifico che i campi non siano nulli in quanto servono sia una data di inizio che di fine prenotazione
            if(TextUtils.isEmpty(dateStart.getText())) {
                dateStart.setError("The item cannot be empty");
                return;
            }else if(TextUtils.isEmpty(hourStart.getText())) {
                hourStart.setError("The item cannot be empty");
                return;
            }else if(TextUtils.isEmpty(dateEnd.getText())) {
                dateEnd.setError("The item cannot be empty");
                return;
            }else if(TextUtils.isEmpty(hourEnd.getText())) {
                hourEnd.setError("The item cannot be empty");
                return;
            }else {


                JSONObject jsonObject = new JSONObject();

                String startOfBook = dateStart.getText().toString().replace("/", "-") + "T" + hourStart.getText().toString() + ":00";
                String endOfBook = dateEnd.getText().toString().replace("/", "-") + "T" + hourEnd.getText().toString() + ":00";
                System.out.println("Inizio prenotazone ############## "+startOfBook);

                //Toast.makeText(BookActivity.this, isAviable(String.valueOf(currentCar.getCarId()),startOfBook,endOfBook)? "ritorna TRUE" : "ritorna FALSE", Toast.LENGTH_SHORT).show();


                // verifico che le date siano corrette, che quindi la fine di prenotazione sia una data
                // successiva a quella di inizio prenotazione
                if(isAfter(endOfBook, startOfBook)) {
                    // Faccio una chiamata al back-end per vedere se la macchina che ha selezionato l'utente è disponibile
                    try {
                        jsonObject.put("startOfBook", startOfBook);
                        jsonObject.put("endOfBook", endOfBook);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AndroidNetworking.post(url + "reservations/availableCars")
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
                            .getAsJSONArray(new JSONArrayRequestListener() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    boolean isAviable = false;
                                    for (int i = 0; i < response.length() && !isAviable; i++) {
                                        try {
                                            // recupero l'oggetto
                                            JSONObject resp = (JSONObject) response.get(i);
                                            // se gli ID tra risposta alle macchine disponibili, e la macchina corrente corrispondono,
                                            // allora setto a true isAviable
                                            //System.out.println("Sto confrontando "+ currentCar.getCarId()+ " " + "con "+ resp.get("carId"));
                                            if (String.valueOf(currentCar.getCarId()).equals(resp.get("carId"))){
                                                isAviable = true;
                                                System.out.println("E disponibileee!!! " + resp.get("carId"));
                                            }else
                                                System.out.println("NON è disponibileee!!! " + resp.get("carId"));

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    // se la macchina è disponibile posso aggiungere la prenotazione
                                    if(isAviable){
                                        makeReservation(startOfBook, endOfBook);
                                    }else
                                        Toast.makeText(view.getContext(), "Macchina non disponibile", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onError(ANError error) {
                                    // handle error
                                    // Qua cosa   fare se la richiesta va in errore
                                    Toast.makeText(BookActivity.this, error.getErrorCode(), Toast.LENGTH_LONG).show();
                                    System.out.println(error);
                                }
                            });


                    //Log.d("Errore", "Macchina non disponibile");
                }else
                    Toast.makeText(BookActivity.this, "La data di inizio prenotazione deve essere precedente a quella di fine prenotazione!!", Toast.LENGTH_SHORT).show();
            }



            /* Per ora va direttamente,
            dopo va fatto che si procede a quella dopo
            selezionando una macchina e passando all'activity
            seguente le informazioni
            * */
            /*Intent intent = new Intent(view.getContext(), PaymentActivity.class);
            // Quindi posso passare il token di autenticazione all'altra activity
            //intent.putExtra("token", token);
            intent.putExtra("currentUser",currentUser);
            intent.putExtra("currentCar",currentCar);
            view.getContext().startActivity(intent);*/

        });
    }


    private void updateLabelStartDate(){
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        dateStart.setText(dateFormat.format(myCalendar.getTime()));
    }
    private void updateLabelStartTime()  {
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.US);
        hourStart.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void updateLabelEndDate(){
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        dateEnd.setText(dateFormat.format(myCalendar.getTime()));
    }
    private void updateLabelEndTime()  {
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.US);
        hourEnd.setText(dateFormat.format(myCalendar.getTime()));
    }



    private void handlingErrors(){
        dateStart.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (dateStart.getText().toString() == "") {
                    dateStart.setError("Enter start Date");
                } else {
                    dateStart.setError(null);
                }
            }
        });

        dateEnd.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (dateEnd.getText().toString() == "") {
                    dateEnd.setError("Enter end Date");
                } else {
                    dateEnd.setError(null);
                }
            }
        });

        hourStart.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (hourStart.getText().toString() == "") {
                    hourStart.setError("Enter start Hour");
                } else {
                    hourStart.setError(null);
                }
            }
        });

        hourEnd.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (hourEnd.getText().toString() == "") {
                    hourEnd.setError("Enter end Hour");
                } else {
                    hourEnd.setError(null);
                }
            }
        });
    }

    private void makeReservation(String startOfBook, String endOfBook){
        JSONObject jsonObject = new JSONObject();

        int rangeMin = -180;
        int rangeMax = 180;
        // genero dei numeri casuali per la posizione
        Position startPosition = new Position(ThreadLocalRandom.current().nextDouble(rangeMin, rangeMax), ThreadLocalRandom.current().nextDouble(rangeMin, rangeMax));
        Position destination = new Position(ThreadLocalRandom.current().nextDouble(rangeMin, rangeMax), ThreadLocalRandom.current().nextDouble(rangeMin, rangeMax));




        try {
            jsonObject.put("startOfBook", startOfBook);
            jsonObject.put("endOfBook", endOfBook);
            jsonObject.put("user", new JSONObject(new Gson().toJson(currentUser)));
            jsonObject.put("car", new JSONObject(new Gson().toJson(currentCar)));
            jsonObject.put("destination", new JSONObject(new Gson().toJson(startPosition)));
            jsonObject.put("startPosition", new JSONObject(new Gson().toJson(destination)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(url + "reservations/add")
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
                        Log.d("Reservation", jsonObject.toString());
                        Toast.makeText(BookActivity.this, "Reservation added SUCESFULLY!", Toast.LENGTH_SHORT).show();


                        // La prenotazione è stata aggiunta con successo, posso passare quindi alla prossima activity
                        Intent intent = new Intent(BookActivity.this, PaymentActivity.class);
                        // Quindi posso passare il token di autenticazione all'altra activity
                        //intent.putExtra("token", token);
                        intent.putExtra("currentUser", currentUser);
                        intent.putExtra("currentCar", currentCar);
                        BookActivity.this.startActivity(intent);

                    }

                    @Override
                    public void onError(ANError anError) {
                        // Qua cosa   fare se la richiesta va in errore
                        Toast.makeText(BookActivity.this, anError.getErrorDetail(), Toast.LENGTH_LONG).show();
                        System.out.println(anError);
                    }
                });
    }

    private boolean isAfter(String endOfBook, String startOfBook){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        Date startDate = null, endDate = null;

        try {
            startDate = format.parse(startOfBook);
            endDate = format.parse(endOfBook);
        } catch (ParseException e) {
            Log.e("Error parsing date", startOfBook + " " + endOfBook);
            e.printStackTrace();
        }

        // la data di fine prenotazione è successiva alla data di inizio prenotazione?

        return endDate.compareTo(startDate) > 0 ? true : false;
    }
}