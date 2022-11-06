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

import model.Car;
import model.Feedback;

public class CustomCarAdapter extends ArrayAdapter<Car> {

    ArrayList<Car> availableCars;
    String token;
    // invoke the suitable constructor of the ArrayAdapter class
    public CustomCarAdapter(@NonNull Context context, ArrayList<Car> availableCars, String token){
        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, availableCars);
        this.availableCars = availableCars;
        this.token = token;
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
        Car currentCar = getItem(position);
        // quindi li assegno alla text view
        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        textView1.setText(currentCar.getName() + " " +currentCar.getCarModel());


        // assegno il valore del commento alla seconda text view
        TextView textView2 = currentItemView.findViewById(R.id.textView2);
        textView2.setText("Battery: " +currentCar.getBattery() + "\n" +currentCar.getPriceHour()+"$/h");


        // then according to the position of the view assign the desired image for the same
        ImageView userImage = currentItemView.findViewById(R.id.imageView);
        //assert currentNumberPosition != null;
        userImage.setImageResource(R.drawable.tesla_model_x);
        // Così quando clicco su una macchina mi manda alla pagina delle specifiche
        currentItemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), StatsCarActivity.class);
            intent.putExtra("token", token);
            intent.putExtra("currentCar",currentCar);
            view.getContext().startActivity(intent);
        });
        return currentItemView;
    }
}


