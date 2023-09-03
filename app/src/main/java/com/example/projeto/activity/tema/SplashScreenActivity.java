package com.example.projeto.activity.tema;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.projeto.R;
import com.example.projeto.activity.InicialActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override public void run() {
                mostrarLogin();
            }
        }, 1500);
    }
    private void mostrarLogin() {
        Intent intent = new Intent(SplashScreenActivity.this, InicialActivity.class);
        startActivity(intent);
        finish();
    }
}