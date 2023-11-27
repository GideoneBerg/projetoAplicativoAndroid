package com.example.projeto.activity.activitys

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.R
import com.example.projeto.activity.classes.FaturasAdapter
import com.example.projeto.activity.classes.Lancamento
import com.example.projeto.activity.classes.QRCodeData
import com.example.projeto.databinding.ActivityFaturasEmAbertoBinding


class FaturasEmAberto : AppCompatActivity() {
    private lateinit var rvLista: RecyclerView
    private lateinit var faturasAdapter: FaturasAdapter
    private var lancamentos: List<Lancamento> = emptyList()
    private var qrCodeDataList: List<QRCodeData> = emptyList()
    private var lancamentosPagos: List<Lancamento> = emptyList()
    private var lancamentosVencidos: List<Lancamento> = emptyList()

    private val binding by lazy {
        ActivityFaturasEmAbertoBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lancamentos =
            intent.getParcelableArrayListExtra("lancamentos", Lancamento::class.java) ?: emptyList()

        qrCodeDataList =
            intent.getParcelableArrayListExtra("qrCode", QRCodeData::class.java) ?: emptyList()

        lancamentosPagos =
            intent.getParcelableArrayListExtra("lancamentosPagos", Lancamento::class.java)
                ?: emptyList()

        lancamentosVencidos =
            intent.getParcelableArrayListExtra("lancamentosVencidos", Lancamento::class.java)
                ?: emptyList()

        if (lancamentos.isNotEmpty() && qrCodeDataList.isNotEmpty()) {
            rvLista = findViewById(R.id.rv_lista)
            val tamanhoLista = lancamentos.size.toString()
            binding.sizeList.text = tamanhoLista
            // , lancamentoPix
            faturasAdapter = FaturasAdapter(lancamentos, qrCodeDataList)
            rvLista.adapter = faturasAdapter

            rvLista.layoutManager = LinearLayoutManager(this)

        } else {
            binding.mensagemTextView.visibility = View.VISIBLE
            binding.rvLista.visibility = View.GONE
        }
        faturasPagas()
        faturasVencidas()
    }

    private fun faturasPagas() {

        binding.btnFaturasPagas.setOnClickListener {
            val intent = Intent(this, FaturasPagas::class.java)
            intent.putParcelableArrayListExtra("lancamentosPagos", ArrayList(lancamentosPagos))
            startActivity(intent)
        }
    }

    private fun faturasVencidas() {
        binding.btnFaturasVencidas.setOnClickListener {
            val intent = Intent(this, FaturasVencidas::class.java)
            intent.putParcelableArrayListExtra("lancamentosVencidos", ArrayList(lancamentosVencidos))
            startActivity(intent)
        }
    }
}