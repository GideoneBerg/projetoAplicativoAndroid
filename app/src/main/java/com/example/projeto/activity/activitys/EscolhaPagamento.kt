package com.example.projeto.activity.activitys

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.projeto.R
import com.example.projeto.activity.classes.Lancamento


import com.example.projeto.databinding.ActivityEscolhaPagamentoBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        val btnCopiarBoleto = binding.copiarCodBarras
        val codigo = binding.codigo.text.toString()

        btnCopiarBoleto.setOnClickListener {
            val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Chave do Boleto", codigo)
            clipBoard.setPrimaryClip(clip)
            CoroutineScope(Dispatchers.Main).launch {
                binding.copiarCodBarras.text = "Chave Copiada"

                delay(3000)

                binding.copiarCodBarras.text = "Copiar"
            }



        }

    }

    private fun snackBar(mensagem: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            mensagem,
            Snackbar.LENGTH_SHORT
        ).setBackgroundTint(ContextCompat.getColor(this, R.color.azulAnil))
            .show()
    }
}