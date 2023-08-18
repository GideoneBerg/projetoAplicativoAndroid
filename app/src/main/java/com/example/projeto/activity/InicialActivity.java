package com.example.projeto.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projeto.R;

public class InicialActivity extends AppCompatActivity {

    private Button botaoWeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        botaoWeb = (Button) findViewById(R.id.button_newCliente);

        botaoWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send/?phone=5581986271986&text&type=phone_number&app_absent=0") ));
            }
        });
    }


    public void button_cliente(View a){
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);

    }

    public void button_planos(View b){
        Intent i = new Intent(this,PlanosActivity.class);
        startActivity(i);

    }
}