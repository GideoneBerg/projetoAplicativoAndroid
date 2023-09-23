package com.example.projeto.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.projeto.databinding.ActivityPoliticaPrivacidadeBinding


class PoliticaPrivacidadeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPoliticaPrivacidadeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoliticaPrivacidadeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)





    }
}