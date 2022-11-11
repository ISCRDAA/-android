package com.example.examen2parcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class PrincipalActivity extends AppCompatActivity {
    static final long SPLASH_SCREEN_DELAY=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setContentView(R.layout.activity_principal);
        TimerTask task= new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(PrincipalActivity.this,MenuActivity.class);
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,SPLASH_SCREEN_DELAY);
    }
    }