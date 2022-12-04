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

import static com.example.bnext.MainActivity.token;
import static com.example.bnext.MainActivity.url;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import model.Feedback;
import model.Reservation;
import model.User;

public class FeedbackDetails extends AppCompatActivity {
    TextView commentTextView, IDTextView;
    Button deleteButton, homeButton;

    // ArrayList che contiene la lista di oggetti Feedback appartenenti ad una macchina (macchina passata da un altra activity)
    Feedback currentFeedback;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_details);

        commentTextView = findViewById(R.id.FeedbackDetailsCommentTextView);
        IDTextView =  findViewById(R.id.FeedbackDetailsIDTextView);
        deleteButton = findViewById(R.id.FeedbackDetailsDeleteButton);
        homeButton = findViewById(R.id.FeedbackDetailsHomeButton);

        Bundle bundle = getIntent().getExtras();
        //token = bundle.getString("token");
        Log.d("Archive Activity",token);
        // Recupero lo user ottenuto dalla pagina di login
        try{
            currentUser = (User) getIntent().getSerializableExtra("user");
            currentFeedback = (Feedback) getIntent().getSerializableExtra("currentFeedback");
            if (currentUser == null || currentFeedback == null)
                throw new NullPointerException();
        } catch (Exception e) {
            Log.e("Feedback Details","Error parsing");
            e.printStackTrace();
        }
        Log.d("Feedback Details","loaded activity Feedback details: " + currentFeedback.toString());

        /*
        * QUI IMPOSTO I VALORI DEI CAMPI DI TESTO
        * */
        commentTextView.setText(currentFeedback.getComment());
        IDTextView.setText(currentFeedback.getIdFeedback().toString());

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), BookRide.class);
            intent.putExtra("currentUser", currentUser);
            //intent.putExtra("currentReservation",currentReservation);
            view.getContext().startActivity(intent);

        });

        deleteButton.setOnClickListener(view -> {
            AndroidNetworking.initialize(getApplicationContext());
            AndroidNetworking.delete(url+"feedbacks/"+currentFeedback.getIdFeedback().toString())
                    .addHeaders("Authorization", "Bearer " + token)
                    .addHeaders("accept", "*/*")
                    .addHeaders("accept-encoding", "gzip, deflate, br")
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsString( new StringRequestListener() {

                              @Override
                              public void onResponse(String s) {
                                  // Toast.makeText(ReservationDetails.this, s, Toast.LENGTH_LONG).show();
                                  Intent intent = new Intent(view.getContext(), FeedbacksArchive.class);
                                  intent.putExtra("user", currentUser);
                                  //intent.putExtra("currentReservation",currentReservation);
                                  view.getContext().startActivity(intent);
                              }

                              @Override
                              public void onError(ANError anError) {
                                  // Qua cosa   fare se la richiesta va in errore
                                  Toast.makeText(FeedbackDetails.this, anError.getErrorDetail(), Toast.LENGTH_LONG).show();
                                  System.out.println(anError);
                              }

                          }
                    );


        });




    }
}