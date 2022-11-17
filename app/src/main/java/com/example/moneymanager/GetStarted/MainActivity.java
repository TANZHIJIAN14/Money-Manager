package com.example.moneymanager.GetStarted;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import com.example.moneymanager.LoginPage.getStarted;
import com.example.moneymanager.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    int counter;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, getStarted.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

        prog();
    }

    public void prog() {
        pb = findViewById(R.id.progressBar);
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter++;
                pb.setProgress(counter);
                if(counter == 2000) {
                    t.cancel();
                }
            }
        };

        t.schedule(tt,0,2000);

    }
}