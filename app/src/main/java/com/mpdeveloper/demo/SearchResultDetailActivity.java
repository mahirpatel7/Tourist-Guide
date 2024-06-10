package com.mpdeveloper.demo;

//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class SearchResultDetailActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search_result_detail);
//
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("title") && intent.hasExtra("info")) {
//            String title = intent.getStringExtra("title");
//            String info = intent.getStringExtra("info");
//
//            TextView textViewTitle = findViewById(R.id.textViewTitleDetail);
//            TextView textViewInfo = findViewById(R.id.textViewInfoDetail);
//            ImageView imageView = findViewById(R.id.imageViewDetail);
//
//            textViewTitle.setText(title);
//            textViewInfo.setText(info);
//
//            // Load an image (Replace R.drawable.ic_launcher with your actual image resource)
//      //      imageView.setImageResource(R.drawable.ic_launcher);
//        }
//    }
//}

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchResultDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("title") && intent.hasExtra("info")) {
            String title = intent.getStringExtra("title");
            String info = intent.getStringExtra("info");

            TextView textViewTitle = findViewById(R.id.textViewTitleDetail);
            TextView textViewInfo = findViewById(R.id.textViewInfoDetail);
            ImageView imageView = findViewById(R.id.imageViewDetail);

            textViewTitle.setText(title);
            textViewInfo.setText(info);

            // Load an image (Replace R.drawable.ic_launcher with your actual image resource)
            //      imageView.setImageResource(R.drawable.ic_launcher);
        }
    }
}
