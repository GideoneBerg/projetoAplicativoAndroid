package com.example.projeto.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.projeto.R;

public class LoginActivity extends AppCompatActivity {

    private TextView botaoAjuda;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        botaoAjuda = (TextView) findViewById(R.id.button_ajuda);

        botaoAjuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send/?phone=5581986271986&text&type=phone_number&app_absent=0") ));
            }
        });


    }
}