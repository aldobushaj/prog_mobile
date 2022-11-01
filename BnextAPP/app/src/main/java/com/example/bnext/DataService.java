package com.example.bnext;

import static android.util.Log.ERROR;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class DataService {
    Context context;

    public DataService(Context context) {
        this.context = context;
    }

    // utilizzata per le richieste al back-end che restituiscono un JSONObject
    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(JSONObject response);
    }
    // utilizzata per le richieste al back-end che restituiscono un JSONArray
    public interface ArrayResponseListener{
        void onError(String message);
        void onResponse(JSONArray response);
    }

    public void login(String username, String password, VolleyResponseListener volleyResponseListener){

        // Ha questo URL in quanto il back-end è in locale, al posto di localhost:8080 si mette questo: 10.0.2.2:8080
        String url = "http://10.0.2.2:8080/user/signin";

        // params conterrà l'header da passare alla richiesta
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        // faccio la conversione in JSON Object dell'hash map per poterlo passare alla richiesta
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            // se è un metodo GET, al posto di parameters devo mettere null
                (Request.Method.POST, url, parameters, response -> {
                    // per accedere ad un determinato campo
                    //response.get("user");
                    volleyResponseListener.onResponse(response);
                    // Log.println(Log.INFO, "result signIn", String.valueOf(response));

                }, error -> {
                    // TODO: Handle error
                    //return error.getStackTrace().toString();
                    volleyResponseListener.onError("Something wrong with the request");
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    error.printStackTrace(pw);
                    String sStackTrace = sw.toString(); // stack trace as a string
                    Log.println(ERROR, "Error in signIn", sStackTrace);


                });//This is for Headers If You Needed

        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }





    public void getCar(String token, ArrayResponseListener arrayResponseListener){

        String url = "http://10.0.2.2:8080/cars";
        //System.out.println("******************** Ecco il token\n"+token);

        JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET,url,null, response ->   {
                    System.out.println("(Successo)Response is "+response);
                    arrayResponseListener.onResponse(response);

            }, error -> {
                    arrayResponseListener.onError("Something wrong with the request\n"+error);
                    System.out.println("Errore");
            })

            {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                //headers.put("Content-Type", "application/json");
                // specifico che l'header è per l'autorizzazione della richiesta, "Bearer " è una parola
                // chiave scelta nel back end che precede il token vero e proprio
                headers.put("Authorization", "Bearer "+token);
                return headers;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

   /*
    public List<CryptoPriceModel> getPriceByName (String cryptoName){
        //TODO
    }
    */



}
