package com.example.media.mediaplayertest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.AudioViewHolder> {
    List<Audio>audios;
    AudioItemOnClickListener audioItemOnClickListener;

    public AudioListAdapter(List<Audio>audios, AudioItemOnClickListener audioItemOnClickListener){
        this.audios = audios;
        this.audioItemOnClickListener = audioItemOnClickListener;
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio, parent, false);
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, final int position) {
        holder.titleTextView.setText(audios.get(position).getTitle());
        holder.descriptionTextView.setText(audios.get(position).getDescription());
        Picasso.get().load(audios.get(position).getThumbnailUrl()).into(holder.thumbnailImageView);
        if(audios.get(position).isPurchased){
            holder.buyTextView.setVisibility(View.GONE);
        }else{
            holder.playTextView.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioItemOnClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return audios.size();
    }

    public class AudioViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView descriptionTextView;
        ImageView thumbnailImageView;
        TextView buyTextView;
        TextView playTextView;

        public AudioViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
            buyTextView = itemView.findViewById(R.id.buyTextView);
            playTextView = itemView.findViewById(R.id.playTextView);
        }
    }
}
