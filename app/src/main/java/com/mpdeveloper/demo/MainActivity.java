package com.mpdeveloper.demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mpdeveloper.demo.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ListView listView;
    private SearchResultAdapter adapter;

    ImageButton mapbutton1;
    private TextView textView;
    ActivityMainBinding binding;

    private ViewFlipper viewFlipper; // Add ViewFlipper
    private static final String PIXABAY_API_KEY = "5ae2e3f221c38a28845f05b6e28cd7d0e64413ae03fe863786af59bf";

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

      //  mapbutton1 = findViewById(R.id.mapbutton1);
        listView = findViewById(R.id.listView);
        viewFlipper = findViewById(R.id.vf); // Initialize ViewFlipper
        textView = findViewById(R.id.tv2);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchWikipedia(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle text changes if needed
                return false;
            }
        });

//        mapbutton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(MainActivity.this, MapActivity.class);
//                startActivity(intent);
//            }
//        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Use adapter.getItem(position) to get the clicked SearchResult object
                SearchResult clickedItem = (SearchResult) adapter.getItem(position);

                // Launch PageDetailActivity and pass necessary data
                startPageDetailActivity(clickedItem.getTitle(), clickedItem.getPageId());
            }
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.tourist_attraction) {
                replaceFragment(new TouristAttractionFragment());
            } else if (itemId == R.id.booking) {
                replaceFragment(new BookingFragment());
            } else {
                replaceFragment(new LoginFragment());
            }

            return true;
        });

    }



    private void searchWikipedia(String query) {
        new Thread(() -> {
            try {
                // Construct Wikipedia API URL for search with extracts
                String apiUrl = "https://en.wikipedia.org/w/api.php?action=query&format=json&list=search&srsearch=" +
                        URLEncoder.encode(query, "UTF-8") + "&prop=extracts|pageimages&pithumbsize=300&exintro=true&explaintext=true";

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

                // Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray searchResults = jsonResponse.getJSONObject("query").getJSONArray("search");
                ArrayList<SearchResult> searchResultList = new ArrayList<>();
                for (int i = 0; i < searchResults.length(); i++) {
                    JSONObject resultObject = searchResults.getJSONObject(i);
                    String title = resultObject.getString("title");
                    int pageId = resultObject.getInt("pageid");
                    String info = ""; // Initialize info with an empty string
                    if (resultObject.has("snippet")) {
                        info = resultObject.getString("snippet");
                    }
                    String imageUrl = ""; // Initialize imageUrl with an empty string
                    if (resultObject.has("thumbnail")) {
                        imageUrl = resultObject.getJSONObject("thumbnail").getString("source");
                    }
                    searchResultList.add(new SearchResult(title, info, pageId, imageUrl));
                }

                // Update UI with search results (in UI thread)
                runOnUiThread(() -> {
                    adapter = new SearchResultAdapter(MainActivity.this, searchResultList);
                    listView.setAdapter(adapter);
                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    private void startPageDetailActivity(String pageTitle, int pageId) {
        Intent intent = new Intent(MainActivity.this, PageDetailActivity.class);
        intent.putExtra("pageTitle", pageTitle);
        intent.putExtra("pageId", pageId);
        startActivity(intent);
    }
}