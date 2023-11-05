package com.example.projeto.activity.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projeto.databinding.ActivityPrimeiroAcessoBinding


class PrimeiroAcesso : AppCompatActivity() {

    private lateinit var binding: ActivityPrimeiroAcessoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrimeiroAcessoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



    }
}