package com.example.projeto.activity.tema;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.projeto.R;
import com.example.projeto.activity.activitys.ClienteActivity;
import com.example.projeto.activity.activitys.InicialActivity;

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
        SharedPreferences prefs = getSharedPreferences("db", MODE_PRIVATE);
        boolean logado = prefs.getBoolean("logado", false);
        String nomeUsuario = prefs.getString("nomeUsuario", "");
        String nascimento = prefs.getString("nascimento", "");
        String plano = prefs.getString("plano", "");
        String cidade = prefs.getString("cidade", "");
        String vencimento = prefs.getString("vencimento", "");
        String rua = prefs.getString("rua", "");
        String bairro = prefs.getString("bairro", "");
        String numero = prefs.getString("numero", "");
        String estado = prefs.getString("estado", "");
        if(logado){
            Intent intent = new Intent(SplashScreenActivity.this, ClienteActivity.class);
            intent.putExtra("nascimento", nascimento);
            intent.putExtra("plano", plano);
            intent.putExtra("cidade", cidade);
            intent.putExtra("vencimento", vencimento);
            intent.putExtra("rua", rua);
            intent.putExtra("numero", numero);
            intent.putExtra("bairro", bairro);
            intent.putExtra("estado", estado);
            intent.putExtra("nomeUsuario", nomeUsuario);
            startActivity(intent);

        }   else {
            Intent intent = new Intent(SplashScreenActivity.this, InicialActivity.class);
            startActivity(intent);
        }
        finish();

    }


}