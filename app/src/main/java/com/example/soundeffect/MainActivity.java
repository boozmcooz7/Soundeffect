package com.example.soundeffect;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = findViewById(R.id.btnStart);
        Button btnStop = findViewById(R.id.btnStop);

        // Start button
        btnStart.setOnClickListener(v -> {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.retrogame); // WAV file in res/raw
                mediaPlayer.setOnCompletionListener(mp -> releasePlayer());
            }
            mediaPlayer.start();
        });

        // Stop button
        btnStop.setOnClickListener(v -> releasePlayer());

        // Alert Dialog Example
        new AlertDialog.Builder(this)
                .setTitle("Retro Game Sound")
                .setMessage("Press Start to play the retro sound!")
                .setPositiveButton("OK", null)
                .show();
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
