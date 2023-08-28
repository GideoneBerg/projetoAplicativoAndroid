package com.example.projeto.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.projeto.R

class ClienteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)


        val nomeUsuario = intent.getStringExtra("nomeUsuario")
        val plano = intent.getStringExtra("plano")
        val vencimento = intent.getStringExtra("vencimento")


        val textViewNome = findViewById<TextView>(R.id.textViewNome)
        textViewNome.text = nomeUsuario


        val textViewPlano = findViewById<TextView>(R.id.textViewPlano)
        textViewPlano.text = plano


        val textViewVencimento = findViewById<TextView>(R.id.textViewVencimento)
        textViewVencimento.text = vencimento


    }
}