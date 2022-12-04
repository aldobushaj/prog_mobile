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

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import model.Car;
import model.Reservation;
import model.User;
public class ArchiveActivity extends AppCompatActivity {
    User currentUser;
    ListView ReservationListView;
    // ArrayList che contiene la lista di oggetti Feedback appartenenti ad una macchina (macchina passata da un altra activity)
     ArrayList<Reservation> reservations = new ArrayList<>();
    Set <Car> allCars= new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archive_activity);
        ReservationListView = findViewById(R.id.ReservationsListView);
        // Recupero il token ottenuto dalla pagina di login
        Bundle bundle = getIntent().getExtras();
        //token = bundle.getString("token");
        Log.d("Archive Activity",token);
        // Recupero lo user ottenuto dalla pagina di login
        try{
        currentUser = (User) getIntent().getSerializableExtra("user");
            if (currentUser == null)
                throw new NullPointerException();
        } catch (Exception e) {
            Log.e("Archive Activity","Error parsing user ");
            e.printStackTrace();
        }
        Log.d("Archive Activity","loaded activity user: " + currentUser.toString());




        /*
        *
        * GET REQUEST PER PRENDERCI L'UTENTE AGGIORNATO
        * */

        //          "https://eo36hxzz25l7d4r.m.pipedream.net"
        //          url+"/user/id={id}"
        AndroidNetworking.get(url+"user/id="+currentUser.getUserId().toString())
                //negli header per il token fare sempre così .addHeaders("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9")
                .addHeaders("Authorization", "Bearer " + token)
                .addHeaders("accept", "*/*")
                .addHeaders("accept-encoding", "gzip, deflate, br")
                //.addPathParameter("id", currentUser.getUserId().toString())
                .addQueryParameter("limit", "10")
                //.addHeaders("content-type","application/json")
                //.setContentType("application/json; charset=utf-8")
                //.addApplicationJsonBody(DateChooseEditText.getText())
                //.addJSONObjectBody(jsonObject) // posting json
                .setPriority(Priority.LOW)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.println(Log.INFO, "Archive Activity", "Getting user");


                        // Qua cosa   fare se la richiesta funziona

                        try {
                            //token = (String) response.get("token");
                            Gson gson  = new GsonBuilder()
                                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                    .create();
                            currentUser = gson.fromJson(response.toString(), User.class); // deserializes json into target2
                            //System.out.println("####################################\n"+currentUser.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        reservations = (ArrayList<Reservation>) currentUser.getReservations();
                        Log.println(Log.INFO, "Archive Activity", "Success getting user reservations" + reservations);
                        //updateReservationsWithCars(reservations);
                        CustomReservationAdapter customViewAdapter = new CustomReservationAdapter(ArchiveActivity.this, reservations,currentUser);

                        // set the CustomViewAdapter for ListView
                        ReservationListView.setAdapter(customViewAdapter);
                    }
                    @Override
                    public void onError(ANError error) {
                        // Qua cosa   fare se la richiesta va in errore
                        Toast.makeText(ArchiveActivity.this, error.getErrorDetail(), Toast.LENGTH_LONG).show();
                        System.out.println(error);
                        Log.println(Log.ERROR, "Archive Activity", error.getErrorDetail());
                    }
                });








    }
    //Set <Car> allCars= new HashSet<>();
    public void updateReservationsWithCars(ArrayList<Reservation> reservations){
       // Set <Car> allCars= new HashSet<>();


        AndroidNetworking.get(url+"cars")
                //negli header per il token fare sempre così .addHeaders("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9")
                .addHeaders("Authorization", "Bearer " + token)
                .addHeaders("accept", "*/*")
                .addHeaders("accept-encoding", "gzip, deflate, br")
                //.addPathParameter("id", currentUser.getUserId().toString())
                //.addQueryParameter("limit", "10")
                //.addHeaders("content-type","application/json")
                //.setContentType("application/json; charset=utf-8")
                //.addApplicationJsonBody(DateChooseEditText.getText())
                //.addJSONObjectBody(jsonObject) // posting json
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONArray(new JSONArrayRequestListener() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.println(Log.INFO, "Archive Activity", "Getting all cars");
                        Car currentCar= null;

                        for (int i=0; i< response.length(); i++) {
                            try {
                                //token = (String) response.get("token");
                                Gson gson  = new GsonBuilder()
                                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                        .create();
                                currentCar = gson.fromJson(response.toString(), Car.class); // deserializes json into target2
                                allCars.add(currentCar);
                                //System.out.println("####################################\n"+currentUser.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                        Log.println(Log.INFO, "Archive Activity", "Success getting all cars" + allCars);
                        for(Reservation currentRes : reservations){
                            for (Car iterCar : allCars){

                                if  (iterCar.getReservation().contains(currentRes)){
                                    currentRes.setCar(iterCar);
                                }

                            }

                        }


                    }
                    @Override
                    public void onError(ANError error) {
                        // Qua cosa   fare se la richiesta va in errore
                        Toast.makeText(ArchiveActivity.this, error.getErrorDetail(), Toast.LENGTH_LONG).show();
                        System.out.println(error);
                        Log.println(Log.ERROR, "Archive Activity", error.getErrorDetail());
                    }
                });



       // return reservations;
    }
}
