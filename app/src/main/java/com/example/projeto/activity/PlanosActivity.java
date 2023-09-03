package com.example.projeto.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.projeto.R;

public class PlanosActivity extends AppCompatActivity {
    private Button central;
    private Button whatsapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planos);

        central = (Button) findViewById(R.id.button_central);
        whatsapp = (Button) findViewById(R.id.button_whatsapp);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send/?phone=5581986271986&text&type=phone_number&app_absent=0")));
            }
        });
        central.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "8134356078";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });
    }
}