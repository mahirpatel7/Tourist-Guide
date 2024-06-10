package com.mpdeveloper.demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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

public class HomeFragment extends Fragment {

    private ListView listView;
    private SearchResultAdapter adapter;

    ImageButton imageButton,menuButton;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.listView);
        SearchView searchView = view.findViewById(R.id.searchView);
        imageButton = view.findViewById(R.id.mapbutton1);
        menuButton = view.findViewById(R.id.menubutton);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchWikipedia(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchResult clickedItem = adapter.getItem(position);
                if (clickedItem != null) {
                    startPageDetailActivity(clickedItem.getTitle(), clickedItem.getPageId());
                }
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        return view;
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
                requireActivity().runOnUiThread(() -> {
                    adapter = new SearchResultAdapter(requireContext(), searchResultList);
                    listView.setAdapter(adapter);
                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void startPageDetailActivity(String pageTitle, int pageId) {
        Intent intent = new Intent(requireContext(), PageDetailActivity.class);
        intent.putExtra("pageTitle", pageTitle);
        intent.putExtra("pageId", pageId);
        startActivity(intent);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        popupMenu.inflate(R.menu.menu);

        // Set up menu item click listener
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.about_us) {
                    Intent intent = new Intent(requireContext() , AboutUs.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.notes) {
                    Intent intent = new Intent(requireContext(), NotesActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.feedback) {
                    openGoogleForm();
                    return true;
                }
                else if (id == R.id.settings) {
                    Intent intent = new Intent(requireContext(), Settings.class);
                    startActivity(intent);
                    return true;
                }
                else {
                    return false;
                }
            }

        });

        // Show the popup menu
        popupMenu.show();
    }

    private void openGoogleForm() {
        // URL of your Google Form
        String googleFormUrl = "https://forms.gle/mofNyCJB2oXZPp3o9";

        // Create an Intent with the URL
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleFormUrl));

        // Start the activity with the Intent
        startActivity(intent);
    }
}
