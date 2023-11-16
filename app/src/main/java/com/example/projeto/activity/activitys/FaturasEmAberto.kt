package com.example.projeto.activity.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.R
import com.example.projeto.activity.classes.FaturasAdapter
import com.example.projeto.activity.classes.Lancamento



class FaturasEmAberto : AppCompatActivity() {
    private lateinit var rvLista: RecyclerView
    private lateinit var faturasAdapter: FaturasAdapter
    private var lancamentos: List<Lancamento> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faturas_em_aberto)


        lancamentos = intent.getParcelableArrayListExtra("lancamentos") ?: emptyList()
        rvLista = findViewById(R.id.rv_lista)

        faturasAdapter = FaturasAdapter(lancamentos)
        rvLista.adapter = faturasAdapter
        rvLista.layoutManager = LinearLayoutManager(this)

    }
}