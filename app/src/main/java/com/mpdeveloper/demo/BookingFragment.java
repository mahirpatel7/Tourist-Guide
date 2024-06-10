package com.mpdeveloper.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class BookingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        CardView CardView = view.findViewById(R.id.booking_card_view1);

        CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String googleFormUrl = "https://www.makemytrip.com/";

                // Create an Intent with the URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleFormUrl));

                // Start the activity with the Intent
                startActivity(intent);

            }
        });

        return view;
    }
}
