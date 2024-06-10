package com.mpdeveloper.first_practical;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class VideoData implements Parcelable {
    private String title;
    private Uri videoUri;
    private String videoPath;

    public VideoData(String title, Uri videoUri, String videoPath) {
        this.title = title;
        this.videoUri = videoUri;
        this.videoPath = videoPath;
    }

    protected VideoData(Parcel in) {
        title = in.readString();
        videoUri = in.readParcelable(Uri.class.getClassLoader());
        videoPath = in.readString();
    }

    public static final Creator<VideoData> CREATOR = new Creator<VideoData>() {
        @Override
        public VideoData createFromParcel(Parcel in) {
            return new VideoData(in);
        }

        @Override
        public VideoData[] newArray(int size) {
            return new VideoData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public Uri getVideoUri() {
        return videoUri;
    }

    public String getVideoPath() {
        return videoPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeParcelable(videoUri, flags);
        dest.writeString(videoPath);
    }
}
