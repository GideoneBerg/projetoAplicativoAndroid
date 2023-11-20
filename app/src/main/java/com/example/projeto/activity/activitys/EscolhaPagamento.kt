package com.example.projeto.activity.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import com.example.projeto.databinding.ActivityEscolhaPagamentoBinding

class EscolhaPagamento : AppCompatActivity() {

    private val binding  by lazy {
        ActivityEscolhaPagamentoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}