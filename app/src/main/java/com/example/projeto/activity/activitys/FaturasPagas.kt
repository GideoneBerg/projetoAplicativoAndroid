package com.example.projeto.activity.activitys

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.R
import com.example.projeto.activity.classes.FaturaAdapterPagas
import com.example.projeto.activity.classes.Lancamento
import com.example.projeto.databinding.ActivityFaturasPagasBinding

class FaturasPagas : AppCompatActivity() {

    private var lancamentosPagos : List<Lancamento> = emptyList()
    private lateinit var rvLista2: RecyclerView
    private lateinit var faturasAdapter: FaturaAdapterPagas

    private val binding by lazy {
        ActivityFaturasPagasBinding.inflate(layoutInflater)
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lancamentosPagos = intent.getParcelableArrayListExtra("lancamentosPagos", Lancamento::class.java)?: emptyList()

        if (lancamentosPagos.isNotEmpty()){
            rvLista2 = findViewById(R.id.rv_listapg)
            //val tamanhoLista = lancamentosPagos.size.toString()
            faturasAdapter = FaturaAdapterPagas(lancamentosPagos)
            rvLista2.adapter = faturasAdapter
            rvLista2.layoutManager = LinearLayoutManager(this)

        } else {

            binding.mensagemTextView.visibility = View.VISIBLE
            binding.rvListapg.visibility = View.GONE
        }
    }
}