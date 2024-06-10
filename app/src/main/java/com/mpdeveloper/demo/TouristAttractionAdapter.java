package com.mpdeveloper.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mpdeveloper.first_practical.VideoData;

import java.util.List;

public class TouristAttractionAdapter extends RecyclerView.Adapter<TouristAttractionAdapter.ViewHolder> {

    private List<VideoData> mData;
    private LayoutInflater mInflater;
    private RecyclerViewInterface mListener;

    TouristAttractionAdapter(Context context, List<VideoData> data, RecyclerViewInterface listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoData item = mData.get(position);
        holder.textView.setText(item.getTitle());
        // Load image using Glide library
        Glide.with(holder.itemView.getContext())
                .load(item.getVideoUri())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvName);
            imageView = itemView.findViewById(R.id.iv1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
