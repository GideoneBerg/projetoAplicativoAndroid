package com.example.projeto.activity.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.projeto.R
import com.example.projeto.activity.classes.ClienteViewModel
import com.example.projeto.activity.model.DataHolder
import com.example.projeto.activity.model.DataHolderLancamento
import com.example.projeto.databinding.ActivityEscolhaPagamentoBinding

class EscolhaPagamento : AppCompatActivity() {




    private lateinit var binding: ActivityEscolhaPagamentoBinding
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityEscolhaPagamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

       val clienteViewModel = ViewModelProvider(this)[ClienteViewModel::class.java]

        clienteViewModel.getCliente().observe(this) { cliente ->
            val nome = cliente.nome
            binding.pix.text = nome
        }



    }
}