package com.mpdeveloper.demo;

//import android.content.Context;
//import android.content.Intent;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//import com.squareup.picasso.Picasso; // Import Picasso library
//import java.util.ArrayList;
//
//public class SearchResultAdapter extends ArrayAdapter<SearchResult> {
//
//    private Context mContext;
//
//    public SearchResultAdapter(Context context, ArrayList<SearchResult> searchResults) {
//        super(context, 0, searchResults);
//        mContext = context;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        final SearchResult searchResult = getItem(position);
//
//        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_search_result, parent, false);
//        }
//
//        // Lookup view for title, snippet, and image
//        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
//        TextView snippetTextView = convertView.findViewById(R.id.snippetTextView);
//        ImageView imageView = convertView.findViewById(R.id.imageView); // Add ImageView
//
//        // Populate the data into the template view using the data object
//        titleTextView.setText(searchResult.getTitle());
//        // Remove HTML tags from snippet and set it in the TextView
//        snippetTextView.setText(Html.fromHtml(searchResult.getSnippet()).toString());
//
//        // Load image using Picasso library
//        if (searchResult.getImageUrl() != null && !searchResult.getImageUrl().isEmpty()) {
//            Picasso.get().load(searchResult.getImageUrl()).into(imageView);
//        } else {
//            // If no image URL is provided, you can set a placeholder image or hide the ImageView
//            imageView.setVisibility(View.GONE);
//        }
//
//        // Set OnClickListener to open PageDetailActivity
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, PageDetailActivity.class);
//                intent.putExtra("pageTitle", searchResult.getTitle());
//                intent.putExtra("pageId", searchResult.getPageId());
//                mContext.startActivity(intent);
//            }
//        });
//
//        // Return the completed view to render on screen
//        return convertView;
//    }
//}

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchResultAdapter extends ArrayAdapter<SearchResult> {

    private Context mContext;

    public SearchResultAdapter(Context context, ArrayList<SearchResult> searchResults) {
        super(context, 0, searchResults);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final SearchResult searchResult = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_search_result, parent, false);
        }

        // Lookup view for title, snippet, and image
        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView snippetTextView = convertView.findViewById(R.id.snippetTextView);
        ImageView imageView = convertView.findViewById(R.id.imageView); // Add ImageView

        // Populate the data into the template view using the data object
        titleTextView.setText(searchResult.getTitle());
        // Remove HTML tags from snippet and set it in the TextView
        snippetTextView.setText(Html.fromHtml(searchResult.getSnippet()).toString());

        // Load image using Picasso library
        if (searchResult.getImageUrl() != null && !searchResult.getImageUrl().isEmpty()) {
            Picasso.get().load(searchResult.getImageUrl()).into(imageView);
        } else {
            // If no image URL is provided, you can set a placeholder image or hide the ImageView
            imageView.setVisibility(View.GONE);
        }

        // Set OnClickListener to open PageDetailActivity
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PageDetailActivity.class);
                intent.putExtra("pageTitle", searchResult.getTitle());
                intent.putExtra("pageId", searchResult.getPageId());
                mContext.startActivity(intent);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
