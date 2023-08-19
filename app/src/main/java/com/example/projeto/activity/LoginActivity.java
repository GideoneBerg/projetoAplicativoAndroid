package com.example.projeto.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto.R;
import com.example.projeto.activity.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private TextView campoCpf, campoSenha, botaoAjuda;
    Button botaoAcessar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializarComponentes();


        botaoAjuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send/?phone=5581986271986&text&type=phone_number&app_absent=0") ));
            }
        });

    }
    public void validarAutenticacao(){
        String cpf = campoCpf.getText().toString();
        String senha = campoSenha.getText().toString();

        if(!cpf.isEmpty()){
            if(!senha.isEmpty()){

                Usuario usuario = new Usuario();

                usuario.setCpf(cpf);
                usuario.setSenha(senha);
                
                logarUsuario();
            } else
                Toast.makeText(this, "Preencher a senha", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(this, "Preencha o CPF", Toast.LENGTH_SHORT).show();

    }

    private void logarUsuario() {
    }

    private void inicializarComponentes(){

        campoCpf = findViewById(R.id.editTextCpf);
        campoSenha = findViewById(R.id.editTextSenha);
        botaoAcessar = findViewById(R.id.buttonAcessar);
        botaoAjuda = (TextView) findViewById(R.id.button_ajuda);

    }
}