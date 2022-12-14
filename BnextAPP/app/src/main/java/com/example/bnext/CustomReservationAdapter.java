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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import model.User;
import model.Reservation;

public class CustomReservationAdapter extends ArrayAdapter<Reservation> {

    ArrayList<Reservation> reservations;
    User user;
    // invoke the suitable constructor of the ArrayAdapter class
    public CustomReservationAdapter(@NonNull Context context,ArrayList<Reservation> reservations, User user){
        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, reservations);
        this.reservations = reservations;
        this.user = user;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, parent, false);
        }
        Reservation currentReservation = getItem(position);
        // quindi li assegno alla text view
        /* TODO: nelle reservation non c'?? ID car, quindi non sappiamo, data la prenotazione
            a quale macchina corrisponde. Le Car hanno le reservation, quindi per adesso
            l'unico modo sarebbe passando da car. Propongo di aggiungere un campo !stringa!
            a Reservation, cos?? da li basta fare una get al server per le car.

            Altrimenti Togliamo il nome della macchina e mettiamo solo Partenza
        * */
        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        //textView1.setText(currentReservation.getCar().getCarModel());
        //textView1.setText(currentReservation.getCar().getName() + " " +currentReservation.getCar().getCarModel());
        //textView1.setText("FIX: no car id in reservation tesla model X");
        //textView1.setText("Start: " +currentReservation.getStartOfBook().toString() + "\n" +"End: "+currentReservation.getEndOfBook().toString());
        // assegno il valore del commento alla seconda text view
        TextView textView2 = currentItemView.findViewById(R.id.textView2);
         textView2.setText("Start: " + currentReservation.getStartOfBook() + "\n" +"End: "+ currentReservation.getEndOfBook());


        // then according to the position of the view assign the desired image for the same
        ImageView userImage = currentItemView.findViewById(R.id.imageView);
        //assert currentNumberPosition != null;
        userImage.setImageResource(R.drawable.tesla_model_x);


        // Cos?? quando clicco su una prenotazione mi manda alla pagina delle specifiche
        currentItemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ReservationDetails.class);
            intent.putExtra("user", user);
            intent.putExtra("currentReservation",currentReservation);
            view.getContext().startActivity(intent);
        });
        return currentItemView;
    }
}



