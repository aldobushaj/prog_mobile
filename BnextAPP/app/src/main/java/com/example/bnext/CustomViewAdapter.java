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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import model.Feedback;

public class CustomViewAdapter extends ArrayAdapter<Feedback> {

    ArrayList<Feedback> feedbacks;

    // invoke the suitable constructor of the ArrayAdapter class
    public CustomViewAdapter(@NonNull Context context, ArrayList<Feedback> feedbacks) {
        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, feedbacks);
        this.feedbacks = feedbacks;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Feedback currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same
        ImageView userImage = currentItemView.findViewById(R.id.imageView);
        //assert currentNumberPosition != null;
        userImage.setImageResource(R.drawable.user_comment);

        // rendo maiuscole la prima lettera del nome e la prima lettera del cognome
        String nome = currentNumberPosition.getUser().getName().substring(0, 1).toUpperCase() + currentNumberPosition.getUser().getName().substring(1);
        String cognome = currentNumberPosition.getUser().getSurname().substring(0, 1).toUpperCase() + currentNumberPosition.getUser().getSurname().substring(1);
        // quindi li assegno alla text view
        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        textView1.setText(nome+" "+cognome);


        // assegno il valore del commento alla seconda text view
        TextView textView2 = currentItemView.findViewById(R.id.textView2);
        textView2.setText(currentNumberPosition.getComment());

        // then return the recyclable view
        return currentItemView;
    }
}

