package com.example.projeto.activity.activitys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto.databinding.ActivityInicialBinding

class InicialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInicialBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityInicialBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        botoes()

    }

    fun botoes(){

        binding.buttonCliente.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        binding.buttonPlanos.setOnClickListener {
            val intent = Intent(this, PlanosActivity::class.java)
            startActivity(intent)

        }

        binding.buttonNewCliente.setOnClickListener {
            val whatsapp =
                "https://api.whatsapp.com/send/?phone=5581986271986&text&type=phone_number&app_absent=0"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(whatsapp))
            startActivity(intent)

        }
    }

}