package com.example.bnext;



import static com.example.bnext.MainActivity.token;
import static com.example.bnext.MainActivity.url;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import model.Car;
import model.User;

public class BookRide extends AppCompatActivity {
    //String token = "";
    Button SearchRideButton, BookRideButton;
    TextView UserNameTextView;
    EditText PriceKmEditText, DestinationEditText, DateChooseEditText, TimeChooseEditText;
    ListView AvailableCarsListView;
    ImageView userAvatar;
    User currentUser;
    String myFormat="yyyy/MM/dd";
    final Calendar myCalendar= Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ride);

        // Recupero il token ottenuto dalla pagina di login
        Bundle bundle = getIntent().getExtras();
        //token = bundle.getString("token");
        Log.d("Token BookRide",token);
        // Recupero lo user ottenuto dalla pagina di login
        currentUser = (User) getIntent().getSerializableExtra("currentUser");
        Date currentTime = Calendar.getInstance().getTime();
        Log.d("User BookRide", currentUser.toString());

        // ---- assign values to each control of the layout----
        //Buttons
        SearchRideButton = findViewById(R.id.SearchRideButton);
        //BookRideButton = findViewById(R.id.bookRideButton);

        // Text Views
        UserNameTextView = findViewById(R.id.UserNameTextView);
        UserNameTextView.setText(currentUser.getName() + " " + currentUser.getSurname() +
                "\n " + currentUser.getUsername());

        // Edit Text

        //PriceKmEditText = findViewById(R.id.PriceKmEditText);
        //DestinationEditText = findViewById(R.id.DestinationEditText);
        DateChooseEditText = findViewById(R.id.DateChooseEditText);
        TimeChooseEditText = findViewById(R.id.TimeChooseEditText);

        // setta la data attuale come ricerca di default
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        String[] parts = currentDate.split(" ");
        DateChooseEditText.setText(parts[0]);
        TimeChooseEditText.setText(parts[1]);



        DatePickerDialog.OnDateSetListener datePicker = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabelEndDate();
        };

        TimePickerDialog.OnTimeSetListener hourPicker = (view, hour, minute ) -> {
            myCalendar.set(Calendar.HOUR_OF_DAY, hour);
            myCalendar.set(Calendar.MINUTE,minute);
            updateLabelEndTime();
        };

        TimeChooseEditText.setOnClickListener(view -> new TimePickerDialog(this, hourPicker,
                myCalendar.get(Calendar.HOUR_OF_DAY),myCalendar.get(Calendar.MINUTE), true).show());


        DateChooseEditText.setOnClickListener(view -> new DatePickerDialog(this, datePicker,
                myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());






        // List View
        AvailableCarsListView = findViewById(R.id.ReservationsListView);


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
            // ArrayList che contiene la lista di oggetti Feedback appartenenti ad una macchina (macchina passata da un altra activity)
            final ArrayList<Car> availableCars = new ArrayList<>();

            final Set<Car> availableCarsSet = new HashSet<>();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            Date date = null;
            String DateToParseString = "";
            try {
                DateToParseString = DateChooseEditText.getText().toString()+"T"+TimeChooseEditText.getText().toString()+":00";
                date = format.parse(DateToParseString);
            } catch (ParseException e) {
                Log.e("Error parsing date",DateChooseEditText.getText().toString());
                e.printStackTrace();
            }
            /*
            * Il backend vuole le date
            * fatte cos?? "2022-07-27T15:00:00"
            *
            * */
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("startOfBook",  DateToParseString);
                jsonObject.put("endOfBook",DateToParseString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //localhost:8080/reservations/availableCars
            // https://eo36hxzz25l7d4r.m.pipedream.net
            AndroidNetworking.post(url+"reservations/availableCars")
                    //negli header per il token fare sempre cos?? .addHeaders("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9")
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
                            // Qua cosa   fare se la richiesta funziona
                            Log.d("Updated user", currentUser.toString());
                            //Toast.makeText(BookRide.this, response.toString(), Toast.LENGTH_LONG).show();

                            //Create the list of avaiable cars
                            for (int i=0; i< response.length(); i++) {
                                try {
                                    // recupero l'oggetto
                                    JSONObject resp = (JSONObject) response.get(i);
                                    Gson gson  = new GsonBuilder()
                                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                            .create();
                                    Car foundCar = gson.fromJson(resp.toString(), Car.class); // deserializes json into target2


                                    availableCars.add(foundCar);
                                    availableCarsSet.add(foundCar);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Log.println(Log.INFO,"Available Cars: ", availableCars.toString());

                            // Now create the instance of the CustomViewAdapter and pass
                            // the context and arrayList created above

                            ArrayList<Car> tempList= new ArrayList<>();
                            tempList.addAll(availableCarsSet);
                            CustomCarAdapter customViewAdapter = new CustomCarAdapter(BookRide.this, tempList,currentUser);

                            // set the CustomViewAdapter for ListView
                            AvailableCarsListView.setAdapter(customViewAdapter);


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
            //intent.putExtra("token", token);
            intent.putExtra("currentUser",currentUser);
            view.getContext().startActivity(intent);

        });
        /*
        BookRideButton.setOnClickListener(view -> {

            Intent intent = new Intent(view.getContext(), BookActivity.class);
            // Quindi posso passare il token di autenticazione all'altra activity
            //intent.putExtra("token", token);
            intent.putExtra("currentUser",currentUser);
            view.getContext().startActivity(intent);

        });*/



    }


    private void updateLabelEndDate(){
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        DateChooseEditText.setText(dateFormat.format(myCalendar.getTime()).replace("/","-"));
    }
    private void updateLabelEndTime()  {
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.US);
        TimeChooseEditText.setText(dateFormat.format(myCalendar.getTime()));
    }

}