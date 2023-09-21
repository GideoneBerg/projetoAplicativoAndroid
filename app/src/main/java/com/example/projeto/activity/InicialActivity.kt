package com.example.projeto.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto.R

class InicialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val whatsapp =
            "https://api.whatsapp.com/send/?phone=5581986271986&text&type=phone_number&app_absent=0"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial)
        val botaoWeb = findViewById<View>(R.id.button_newCliente) as Button
        botaoWeb.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(whatsapp)
                )
            )
        }
    }

    fun button_cliente(a: View?) {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
    }

    fun button_planos(b: View?) {
        val i = Intent(this, PlanosActivity::class.java)
        startActivity(i)
    }
}