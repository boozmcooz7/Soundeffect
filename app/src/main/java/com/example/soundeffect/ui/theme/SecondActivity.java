package com.example.soundeffect.ui.theme;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

import com.example.soundeffect.R;

public class SecondActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Thread playThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnStart = findViewById(R.id.btnStartThread);
        Button btnStop = findViewById(R.id.btnStopThread);
        Button btnBack = findViewById(R.id.btnBackToFirst);

        btnStart.setOnClickListener(v -> startWithThread());
        btnStop.setOnClickListener(v -> confirmStop());
        btnBack.setOnClickListener(v ->
                startActivity(new Intent(SecondActivity.this, com.example.soundeffect.MainActivity.class)));
    }

    private void ensurePlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.retrogame); // res/raw/sound.wav
            mediaPlayer.setOnCompletionListener(mp -> releasePlayer());
        }
    }

    private void startWithThread() {
        ensurePlayer();
        if (mediaPlayer == null || mediaPlayer.isPlaying()) return;

        playThread = new Thread(() -> {
            try {
                mediaPlayer.start();
            } catch (IllegalStateException ignored) {}
        });
        playThread.start();
    }

    private void stopNow() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            } catch (IllegalStateException ignored) {}
            releasePlayer();
        }
        playThread = null;
    }

    private void releasePlayer() {
        try {
            mediaPlayer.reset();
        } catch (Exception ignored) {}
        try {
            mediaPlayer.release();
        } catch (Exception ignored) {}
        mediaPlayer = null;
    }

    private void confirmStop() {
        new AlertDialog.Builder(this)
                .setTitle("Stop sound")
                .setMessage("Are you sure you want to stop?")
                .setPositiveButton("Yes", (d, w) -> stopNow())
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopNow();
    }

    @Override
    protected void onDestroy() {
        stopNow();
        super.onDestroy();
    }
}
