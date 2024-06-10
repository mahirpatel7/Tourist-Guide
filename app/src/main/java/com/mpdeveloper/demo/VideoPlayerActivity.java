package com.mpdeveloper.demo;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.mpdeveloper.first_practical.VideoData;

import java.util.ArrayList;

public class VideoPlayerActivity extends AppCompatActivity {

    private ArrayList<VideoData> videoDataList;

    ImageButton arrowback;

    @SuppressLint({"NewApi", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        arrowback = findViewById(R.id.videoplayerarrowback);

        // Retrieve the videoDataList from the intent extras
        ArrayList<Parcelable> parcelableList = getIntent().getParcelableArrayListExtra("VIDEO_DATA_LIST");

        // Check if the parcelableList is not null and contains items
        if (parcelableList != null && !parcelableList.isEmpty()) {
            // Initialize the videoDataList
            videoDataList = new ArrayList<>();

            // Convert each Parcelable object to VideoData and add it to the videoDataList
            for (Parcelable parcelable : parcelableList) {
                if (parcelable instanceof VideoData) {
                    videoDataList.add((VideoData) parcelable);
                }
            }
        }

        // Initialize the VideoView
        VideoView videoView = findViewById(R.id.videoView);

        // Initialize the MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Retrieve the clicked position from the intent extras
        int clickedPosition = getIntent().getIntExtra("CLICKED_POSITION", 0);

        // Ensure videoDataList is not null and contains items
        if (videoDataList != null && !videoDataList.isEmpty() && clickedPosition >= 0 && clickedPosition < videoDataList.size()) {
            // Retrieve the video path based on the clicked position
            String videoPath = videoDataList.get(clickedPosition).getVideoPath();

            // Set the video URI
            videoView.setVideoURI(Uri.parse(videoPath));

            // Start playing the video
            videoView.start();

            arrowback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Finish the current activity to return to the previous activity in the back stack
                    finish();
                }
            });
        }
    }
}
