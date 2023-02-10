package com.example.audio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.icu.lang.UProperty;
import android.media.AudioManager;
import android.net.Uri;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import java.util.HashMap;
import java.io.IOException;

import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Button;
import android.widget.RadioGroup;
import android.media.MediaPlayer;
import android.widget.RadioButton;
import android.media.AudioAttributes;
import androidx.appcompat.app.AppCompatActivity;

import kotlin.reflect.KProperty;

public class MainActivity extends AppCompatActivity {

    MediaPlayer player;
    Spinner ddlEnglish, ddlSpanish;

    String soundUrl = "http://soundfxcenter.com/human/speech/8d82b5_The_Number_1_Sound_Effect.mp3";

     Map<String, String> sounds = new HashMap<String, String>(){{
        put("1","https://freetts.com/audio/1d78b283-5075-4375-8831-8e2399fdfc05.mp3");
        put("2","https://freetts.com/audio/0ac632a8-0d48-4569-8ff1-b63570d0d877.mp3");
        put("3","https://freetts.com/audio/3cb9413f-e0b8-400e-90d2-4b32025c8634.mp3");
        put("4","https://freetts.com/audio/4bcbbc14-9173-4f8c-b54f-d692144f43fd.mp3");
        put("5","https://freetts.com/audio/65c9b8b5-0662-47ec-adf5-75bfbb0d6fd9.mp3");
        put("6","https://freetts.com/audio/49f044b8-d1cb-4f1c-87a0-f2b2242d4dc4.mp3");
        put("7","https://freetts.com/audio/2dedccce-5109-4173-92b1-984050ffbbbc.mp3");
        put("8","https://freetts.com/audio/c16e21ad-ac77-4396-8353-d78345cad1aa.mp3");
        put("9","https://freetts.com/audio/0c712af1-bf3c-4091-afbc-27ddb8d21973.mp3");
        put("10","https://freetts.com/audio/d0995cdb-30e7-4649-8a32-0414a91095f6.mp3");

        put("11","https://freetts.com/audio/0c966fd0-93db-4239-965c-b68f43494764.mp3");
        put("12","https://freetts.com/audio/b1ef3322-c6b4-41dc-b6bb-c36d65782c6c.mp3");
        put("13","https://freetts.com/audio/bed3f571-3e7f-405f-a8b7-20b79e7423c5.mp3");
        put("14","https://freetts.com/audio/d49b973d-e0b2-4e6e-b95f-027a0db999c5.mp3");
        put("15","https://freetts.com/audio/cfa1eaa2-39c0-4bda-8f83-cedceda9d823.mp3");
        put("16","https://freetts.com/audio/2d7a9142-1ba6-46a2-b478-27567804b42f.mp3");
        put("17","https://freetts.com/audio/d55d5d8b-93f6-4ef1-8837-4d65ae171ab7.mp3");
        put("18","https://freetts.com/audio/b03b9278-154f-49d0-9355-c01ee64c9d4b.mp3");
        put("19","https://freetts.com/audio/e4364098-ced2-44a7-b4c6-a9a439d7c8a7.mp3");
        put("20","https://freetts.com/audio/957dda43-77a1-41fe-b545-bb2586aece34.mp3");

        put("-1","https://freetts.com/audio/5d59b32e-bfdf-4420-9e66-a9feeb38eefb.mp3");

    }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ddlEnglish = findViewById(R.id.ddlEnglish);
        ddlSpanish = findViewById(R.id.ddlSpanish);

        loadNumbers(ddlEnglish, ddlSpanish);

        Button btnPlay = findViewById(R.id.btnPlay);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = "Please, select an option";
                String option = "-1";//String.valueOf(selected);

                numbers english = (numbers) ddlEnglish.getSelectedItem();
                numbers spanish = (numbers) ddlSpanish.getSelectedItem();

                try {
                    if(player != null){
                        if(player.isPlaying()){
                            player.release();
                            player = null;
                        }
                    }

                    if(english.getKey() != -1 || spanish.getKey() != -1){
                        if(english.getKey() != -1 && spanish.getKey() == -1){
                            message = "You have selected: " + english.getKey();
                            option = String.valueOf(english.getKey());
                        }else{
                            option = String.valueOf(spanish.getKey());
                            message = "Has seleccionado: " + spanish.getValue().replace("Número ", "");
                        }
                    }



                    player = new MediaPlayer();
                    player.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build());

                    player.setDataSource(sounds.get(option));

                    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
                        }
                    });

                    player.prepareAsync();
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                   Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                   Log.e("ERROR", e.getMessage());
                }

                ddlEnglish.setSelection(0);
                ddlSpanish.setSelection(0);
            }

        });

    }

    private void loadNumbers(Spinner ddlEnglish, Spinner ddlSpanish){

        List<numbers> englishNumbers = new ArrayList<>();
        List<numbers> spanishNumbers = new ArrayList<>();

        englishNumbers.add(new numbers(-1,"--- Select a number ---"));
        spanishNumbers.add(new numbers(-1,"--- Seleccione un número ---"));
        int c = 1;

        for (int x = 1; x <= 20; x++){
            if(x <= 10){
                englishNumbers.add(new numbers(x,"Number " + x));
            }
            else{
                spanishNumbers.add(new numbers(x,"Número " + c));
                c++;
            }
        }
        ddlEnglish.setAdapter(new ArrayAdapter<numbers>(this, android.R.layout.simple_spinner_item, englishNumbers));
        ddlSpanish.setAdapter(new ArrayAdapter<numbers>(this, android.R.layout.simple_spinner_item, spanishNumbers));

    }

}