package com.example.media.mediaplayertest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wseemann.media.FFmpegMediaMetadataRetriever;

import static android.view.View.GONE;

public class AudioListFragment extends Fragment implements AudioItemOnClickListener{

    RecyclerView audioListRecyclerView;
    TextView titleTextView;
    AudioListAdapter audioListAdapter;
    List<Audio>audios;
    int currentAudio, previousAudio, nextAudio;
    MediaPlayer mediaPlayer;
    ImageView playImageView, pauseImageView, nextImageView, previousImageView;
    ConstraintLayout controller;
    FFmpegMediaMetadataRetriever retriever;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        audios = new ArrayList<>();
        Audio audio1 = new Audio("Girls Like You",
                "Maroon 5 Featuring Cardi B",
                "http://hiphopde.com/wp-content/uploads/2018/06/01%20Girls%20Like%20You%20(feat.%20Cardi%20B).mp3",
                "https://charts-static.billboard.com/img/2018/06/maroon-5-9st-girls-like-you-32b-53x53.jpg",
                true);
        audios.add(audio1);

        Audio audio2 = new Audio("Kaliya",
                "Kaliya by Rajib",
                "http://banglasongs.fusionbd.com/downloads/download.php?file=mp3/bangla/Top_100_Songs_Of_2010/082.Rajib-Kaliya.mp3",
                "http://onsongapp.s3.amazonaws.com/manual/55d62a1640ac50dc8544cb4524418f1c.png",
                true);
        audios.add(audio2);

        Audio audio3 = new Audio("Ei Je Ami",
                "Stoic Bliss - Kazi - Ei Je Ami",
                "http://download.music.com.bd/Music/S/Stoic%20Bliss/Kolponar%20Baire/09%20-%20Stoic%20Bliss%20-%20Ei%20Je%20Ami%20(music.com.bd).mp3",
                "http://onsongapp.s3.amazonaws.com/manual/55d62a1640ac50dc8544cb4524418f1c.png",
                false);
        audios.add(audio3);

        Audio audio4 = new Audio("Krishnochura",
                "Krishnochura by Aurthohin",
                "http://download.music.com.bd/Music/A/Aurthohin/Notun%20Diner%20Michile/03%20-%20Aurthohin%20-%20Krishnochura%20(music.com.bd).mp3",
                "http://onsongapp.s3.amazonaws.com/manual/55d62a1640ac50dc8544cb4524418f1c.png",
                true);
        audios.add(audio4);

        currentAudio = -1;
        nextAudio = -1;
        previousAudio = -1;

        return inflater.inflate(R.layout.fragment_audio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        audioListAdapter = new AudioListAdapter(audios, this);

        audioListRecyclerView = view.findViewById(R.id.audioListRecyclerView);
        playImageView = view.findViewById(R.id.playImageView);
        pauseImageView = view.findViewById(R.id.pauseImageView);
        nextImageView = view.findViewById(R.id.nextImageView);
        previousImageView = view.findViewById(R.id.previousImageView);
        controller = view.findViewById(R.id.mediaController);
        titleTextView = view.findViewById(R.id.titleTextView);
        pauseImageView.setVisibility(View.VISIBLE);
        playImageView.setVisibility(GONE);
        controller.setVisibility(GONE);
        audioListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        audioListRecyclerView.setAdapter(audioListAdapter);

        playImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentAudio!=-1)
                    play(currentAudio);
            }
        });

        pauseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });

        nextImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nextAudio!=-1){
                    play(nextAudio);
                }
            }
        });

        previousImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(previousAudio!=-1){
                    play(previousAudio);
                }
            }
        });

    }

    @Override
    public void onClick(int currentAudio) {
        if(audios.get(currentAudio).isPurchased)
            play(currentAudio);
        else{
            Toast.makeText(getContext(), "Buy this audio first", Toast.LENGTH_SHORT).show();
        }
    }

    private void pause(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else{
            play(currentAudio);
        }
        playImageView.setVisibility(View.VISIBLE);
        pauseImageView.setVisibility(GONE);
    }

    private void play(int currentAudio) {
        DisplayNotification displayNotification = new DisplayNotification(getContext());
        displayNotification.makeNotification();
        titleTextView.setText(audios.get(currentAudio).getTitle());
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WAKE_LOCK);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WAKE_LOCK}, 101);
        }else {
            if (currentAudio != this.currentAudio) {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(getContext(), Uri.parse(audios.get(currentAudio).streamingUrl));
                mediaPlayer.start();
                controller.setVisibility(View.VISIBLE);
                playImageView.setVisibility(View.INVISIBLE);
                pauseImageView.setVisibility(View.VISIBLE);
                refreshPosition(currentAudio);
            }else{
                if(!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    controller.setVisibility(View.VISIBLE);
                    playImageView.setVisibility(View.INVISIBLE);
                    pauseImageView.setVisibility(View.VISIBLE);
                }
                else {
                    pause();
                }
            }
            this.currentAudio = currentAudio;
        }

    }

    private void refreshPosition(int currentAudio) {
        boolean isNextAudioDetected = false;
        for(Audio audio : audios){
            if(audios.indexOf(audio)<currentAudio && audio.isPurchased)
                previousAudio = audios.indexOf(audio);
            else if(audios.indexOf(audio)>currentAudio && audio.isPurchased && !isNextAudioDetected){
                nextAudio = audios.indexOf(audio);
                isNextAudioDetected = true;
            }
        }
    }
}
