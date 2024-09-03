package com.mgke.lb1;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView timeTextView;
    private Button startButton, stopButton, resetButton;
    private Handler handler;
    private long startTime, elapsedTime = 0L;
    private boolean isRunning = false;

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - startTime;

            int seconds = (int) (elapsedTime / 1000) % 60;
            int minutes = (int) (elapsedTime / (1000 * 60)) % 60;
            int hours = (int) (elapsedTime / (1000 * 60 * 60)) % 24;

            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            timeTextView.setText(timeString);

            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeTextView = findViewById(R.id.timeTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);

        handler = new Handler();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startTime = System.currentTimeMillis() - elapsedTime;
                    handler.postDelayed(updateTimer, 0);
                    isRunning = true;
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    handler.removeCallbacks(updateTimer);
                    isRunning = false;
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(updateTimer);
                elapsedTime = 0L;
                timeTextView.setText("00:00:00");
                isRunning = false;
            }
        });
    }
}
