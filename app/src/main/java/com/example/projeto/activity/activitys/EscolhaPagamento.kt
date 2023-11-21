package com.example.projeto.activity.activitys

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.projeto.activity.classes.Lancamento


import com.example.projeto.databinding.ActivityEscolhaPagamentoBinding

class EscolhaPagamento : AppCompatActivity() {

    private val binding  by lazy {
        ActivityEscolhaPagamentoBinding.inflate(layoutInflater)
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val lancamento = intent.getParcelableExtra("key", Lancamento::class.java)
        if(lancamento != null){
            binding.idFatura.text = lancamento.titulo
            binding.statusFatura.text = lancamento.status
            binding.valor.text = lancamento.valor
            binding.vencimento.text = lancamento.datavenc
            binding.codigo.text = lancamento.linhadig

        }




    }
}