package com.mpdeveloper.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TouristAttractionFragment extends Fragment implements RecyclerViewInterface {

    private RecyclerView recyclerView;
    private TouristAttractionAdapter videoAdapter;
    private List<com.mpdeveloper.first_practical.VideoData> videoDataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tourist_attraction, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Prepare data for RecyclerView
        prepareVideoData();

        // Initialize and set adapter for RecyclerView
        videoAdapter = new TouristAttractionAdapter(getActivity(), videoDataList, this);
        recyclerView.setAdapter(videoAdapter);

        return view;
    }

    private void prepareVideoData() {
        // Initialize list of video data
        videoDataList = new ArrayList<>();

        // Add video data items to the list
        videoDataList.add(new com.mpdeveloper.first_practical.VideoData("Heritage Places", Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.heritage_week), "android.resource://" + getActivity().getPackageName() + "/" + R.raw.heritage_week));
        videoDataList.add(new com.mpdeveloper.first_practical.VideoData("Forts of Telangana", Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.forts_telngana), "android.resource://" + getActivity().getPackageName() + "/" + R.raw.forts_of_telangana));
        videoDataList.add(new com.mpdeveloper.first_practical.VideoData("Jammu and Kashmir", Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.jammu_kashmir), "android.resource://" + getActivity().getPackageName() + "/" + R.raw.jammu_kashmir));
        videoDataList.add(new com.mpdeveloper.first_practical.VideoData("Gujarat", Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.updated_dwarka), "android.resource://" + getActivity().getPackageName() + "/" + R.raw.gujarat));
        videoDataList.add(new com.mpdeveloper.first_practical.VideoData("Tamil Nadu", Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.tamil_nadu), "android.resource://" + getActivity().getPackageName() + "/" + R.raw.tamil_nadu));
        videoDataList.add(new com.mpdeveloper.first_practical.VideoData("Delhi", Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.delhi), "android.resource://" + getActivity().getPackageName() + "/" + R.raw.namaste_delhi));
        videoDataList.add(new com.mpdeveloper.first_practical.VideoData("Taj Mahal", Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.taj_mahal), "android.resource://" + getActivity().getPackageName() + "/" + R.raw.taj_mahal));
        videoDataList.add(new com.mpdeveloper.first_practical.VideoData("Badrinath Temple", Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.badrinath), "android.resource://" + getActivity().getPackageName() + "/" + R.raw.badrinath_uttarakhand));
        videoDataList.add(new com.mpdeveloper.first_practical.VideoData("Ajanta and Ellora Caves", Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.ajanta_ellora_caves), "android.resource://" + getActivity().getPackageName() + "/" + R.raw.ajanta_and_ellora_caves));
        videoDataList.add(new com.mpdeveloper.first_practical.VideoData("Fatehpur Sikri", Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.fatehpur_sikri), "android.resource://" + getActivity().getPackageName() + "/" + R.raw.fatehpur_sikri));
        videoDataList.add(new com.mpdeveloper.first_practical.VideoData("Khajuraho Temple", Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.khajurahp_temple), "android.resource://" + getActivity().getPackageName() + "/" + R.raw.khajuraho_temple));
        // Add more video data items as needed
    }

    @Override
    public void onItemClick(int position) {
        // Retrieve the clicked VideoData
        com.mpdeveloper.first_practical.VideoData clickedVideo = videoDataList.get(position);

        // Create an intent to start MainActivity2
        Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);

        // Pass the videoDataList and clicked position to MainActivity2
        ArrayList<Parcelable> parcelableList = new ArrayList<>();
        for (com.mpdeveloper.first_practical.VideoData videoData : videoDataList) {
            parcelableList.add(videoData);
        }
        intent.putParcelableArrayListExtra("VIDEO_DATA_LIST", parcelableList);
        intent.putExtra("CLICKED_POSITION", position);

        // Start MainActivity2
        startActivity(intent);
    }
}
