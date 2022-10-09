package com.example.weatherapiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String token = "";
    Button btn_cityID, btn_getWeatherByID, btn_getWeatherByName;
    EditText et_dataInput;
    ListView lv_weatherReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // assign values to each control of the layout
        btn_cityID = findViewById(R.id.btn_getCityID);
        btn_getWeatherByID = findViewById(R.id.btn_getWeatherByCityID);
        btn_getWeatherByName = findViewById(R.id.btn_getWeatherByCityName);
        et_dataInput = findViewById(R.id.et_dataInput);
        lv_weatherReport = findViewById(R.id.lv_weatherReports);

        //click listeners
        btn_cityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataService  dataService = new DataService(MainActivity.this);

                dataService.signIn(new DataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this,"Something wrong MainActivity CityID  " , Toast.LENGTH_SHORT).show();
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

            }
        });








        btn_getWeatherByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataService  dataService = new DataService(MainActivity.this);

                dataService.getCar(token, new DataService.ArrayResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message , Toast.LENGTH_SHORT).show();
                    }

                   @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(MainActivity.this,"Value of data : " + response, Toast.LENGTH_SHORT).show();
                    }
                });

                //Log.println(Log.INFO, "signIn result", "bitcoin");
                //Toast.makeText(MainActivity.this,"Value of data : " + bitcoin, Toast.LENGTH_SHORT).show();

            }
            /*@Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You clicked me 2", Toast.LENGTH_SHORT).show();
            }*/
        });







        btn_getWeatherByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, StatsCarActivity.class);
                startActivity(intent);
                /*Toast.makeText(
                        MainActivity.this,
                        "You typed " + et_dataInput.getText().toString(),
                        Toast.LENGTH_SHORT).show();*/
            }
        });
    }
}