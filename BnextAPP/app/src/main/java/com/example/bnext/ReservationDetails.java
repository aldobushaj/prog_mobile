/*
 * Copyright (c) 2022.
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.bnext;

/*
 * Copyright (c) 2022.
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


import static com.example.bnext.MainActivity.token;
import static com.example.bnext.MainActivity.url;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import model.Reservation;
import model.User;
public class ReservationDetails extends AppCompatActivity {
    TextView dateTextView, timeTextView;
    Button deleteButton, homeButton;

    // ArrayList che contiene la lista di oggetti Feedback appartenenti ad una macchina (macchina passata da un altra activity)
    Reservation currentReservation;
    User currentUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);
        dateTextView = findViewById(R.id.ReservationDetailsDateTextView);
        timeTextView =  findViewById(R.id.ReservationDetailsDTimexTextView);
        deleteButton = findViewById(R.id.ReservationDetailsDeleteButton);
        homeButton = findViewById(R.id.ReservationDetailsHomeButton);

          // Recupero il token ottenuto dalla pagina di login
        Bundle bundle = getIntent().getExtras();
        //token = bundle.getString("token");
        Log.d("Archive Activity",token);
        // Recupero lo user ottenuto dalla pagina di login
        try{
            currentUser = (User) getIntent().getSerializableExtra("user");
            currentReservation = (Reservation) getIntent().getSerializableExtra("currentReservation");
            if (currentUser == null || currentReservation == null)
                throw new NullPointerException();
        } catch (Exception e) {
            Log.e("Reservation Details","Error parsing");
            e.printStackTrace();
        }
        Log.d("Reservation Details","loaded activity reservation: " + currentReservation.toString());


        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss yyyy",
                Locale.ENGLISH);
        Date date = null;
        String DateToParseString = "";
        try {
            //Log.d("Reservation Details", String.valueOf(currentReservation.getStartOfBook().toInstant().getEpochSecond()));
            DateToParseString = currentReservation.getStartOfBook().toString();
            //Wed Jul 27 15:00:00 GMT+02:00 2022
            String[] chunks = DateToParseString.split(" ");
            //Date parsedDate  = sdf.parse(chunks[0]+" "+chunks[1]+ " " + chunks[2] + " " + chunks[3] + " " + chunks[4]);


            //SimpleDateFormat printParser = new SimpleDateFormat("MMM d yyyy HH:mm:ss");



            dateTextView.setText(chunks[0]+" "+chunks[1]+ " " + chunks[2] +" "+ chunks[5] );
            timeTextView.setText(chunks[3]);

          Log.d("Reservation Details","setted dates");

        } catch (Exception e) {
            Log.e("Error parsing date",currentReservation.getStartOfBook().toString());
            e.printStackTrace();
        }
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), BookRide.class);
            intent.putExtra("currentUser", currentUser);
            //intent.putExtra("currentReservation",currentReservation);
            view.getContext().startActivity(intent);

        });
        deleteButton.setOnClickListener(view -> {
            AndroidNetworking.initialize(getApplicationContext());
            AndroidNetworking.delete(url+"reservations/delete="+currentReservation.getReservationId().toString())
                    .addHeaders("Authorization", "Bearer " + token)
                    .addHeaders("accept", "*/*")
                    .addHeaders("accept-encoding", "gzip, deflate, br")
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsString( new StringRequestListener() {



                                      @Override
                                      public void onResponse(String s) {
                                         // Toast.makeText(ReservationDetails.this, s, Toast.LENGTH_LONG).show();
                                          Intent intent = new Intent(view.getContext(), ArchiveActivity.class);
                                          intent.putExtra("user", currentUser);
                                          //intent.putExtra("currentReservation",currentReservation);
                                          view.getContext().startActivity(intent);
                                      }

                                      @Override
                                      public void onError(ANError anError) {
                                          // Qua cosa   fare se la richiesta va in errore
                                          Toast.makeText(ReservationDetails.this, anError.getErrorDetail(), Toast.LENGTH_LONG).show();
                                          System.out.println(anError);
                                      }

                                  }
                    );


        });



        }

        /*
         *
         * GET REQUEST PER PRENDERCI L'UTENTE AGGIORNATO
         * */

        //          "https://eo36hxzz25l7d4r.m.pipedream.net"
        //          url+"reservations/delete="









    }

