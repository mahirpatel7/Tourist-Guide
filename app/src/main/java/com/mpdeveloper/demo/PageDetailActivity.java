package com.mpdeveloper.demo;

//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class PageDetailActivity extends AppCompatActivity {
//
//    private TextView contentTextView;
//    private String pageTitle;
//    private int pageId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_page_detail);
//
//        contentTextView = findViewById(R.id.contentTextView);
//
//        // Retrieve data passed from MainActivity
//        Intent intent = getIntent();
//        pageTitle = intent.getStringExtra("pageTitle");
//        pageId = intent.getIntExtra("pageId", 0);
//
//        // Make API call to fetch page details from Wikipedia
//        fetchPageDetails(pageTitle, pageId);
//    }
//
//    private void fetchPageDetails(String pageTitle, int pageId) {
//        new Thread(() -> {
//            try {
//                // Construct Wikipedia API URL for page details
//                String apiUrl = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro=true&pageids=" + pageId;
//
//                // Create HTTP connection
//                URL url = new URL(apiUrl);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//
//                // Read response
//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//
//                // Parse JSON response
//                JSONObject jsonResponse = new JSONObject(response.toString());
//                JSONObject pages = jsonResponse.getJSONObject("query").getJSONObject("pages");
//                JSONObject page = pages.getJSONObject(String.valueOf(pageId));
//                String extract = page.getString("extract");
//
//                // Update UI with page details (in UI thread)
//                runOnUiThread(() -> {
//                    contentTextView.setText(extract);
//                });
//
//            } catch (IOException | JSONException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
//}

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class PageDetailActivity extends AppCompatActivity {

    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_detail);

        contentTextView = findViewById(R.id.contentTextView);

        // Retrieve data passed from MainActivity
        Intent intent = getIntent();
        String pageTitle = intent.getStringExtra("pageTitle");

        // Make API call to fetch full page content
        new FetchPageContentTask().execute(pageTitle);
    }

    private class FetchPageContentTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String pageTitle = params[0];
            try {
                // Construct Wikipedia API URL for page content
                String apiUrl = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro=true&explaintext=true&titles=" +
                        URLEncoder.encode(pageTitle, "UTF-8");

                // Create HTTP connection
                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                // Read response
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse JSON response to extract page content
                return parseResponse(response.toString());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String content) {
            // Display full page content in TextView
            if (content != null && !content.isEmpty()) {
                contentTextView.setText(removeUnknownCharacters(content));
            } else {
                contentTextView.setText("Failed to fetch page content");
            }
        }

        private String parseResponse(String response) {
            try {
                // Extract page content from JSON response
                String content = "";
                if (response != null && !response.isEmpty()) {
                    int startIndex = response.indexOf("extract\":\"") + 10; // Start index of content
                    int endIndex = response.lastIndexOf("\"}"); // End index of content
                    if (startIndex != -1 && endIndex != -1) {
                        content = response.substring(startIndex, endIndex);
                    }
                }

                // Split content into paragraphs
                List<String> paragraphs = splitIntoParagraphs(content);
                // Join paragraphs with newline characters
                return TextUtils.join("\n\n", paragraphs);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private List<String> splitIntoParagraphs(String content) {
            List<String> paragraphs = new ArrayList<>();
            // Split content into sentences
            String[] sentences = content.split("\\.\\s+");
            StringBuilder paragraph = new StringBuilder();
            for (String sentence : sentences) {
                // Add sentence to paragraph
                paragraph.append(sentence).append(". ");
                // If the paragraph reaches a certain length, add it to the list and start a new paragraph
                if (paragraph.length() > 200) {
                    paragraphs.add(paragraph.toString());
                    paragraph = new StringBuilder();
                }
            }
            // Add the last paragraph to the list
            if (paragraph.length() > 0) {
                paragraphs.add(paragraph.toString());
            }
            return paragraphs;
        }

        private String removeUnknownCharacters(String content) {

            return content.replaceAll("\\\\[uU][0-9a-fA-F]{4}|\\\\n", "");
        }
    }
}