package com.example.projeto.activity.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.projeto.R
import com.example.projeto.activity.classes.Mensagem
import com.example.projeto.activity.classes.MensagemAdapter
import com.example.projeto.activity.classes.RetrofitService
import com.example.projeto.activity.interfaces.FaturasAberto
import retrofit2.create

class FaturasEmAberto : AppCompatActivity() {
    private lateinit var serviceFaturas: FaturasAberto
    private lateinit var rvLista: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faturas_em_aberto)

        serviceFaturas = RetrofitService.getRetrofitInstance()
            .create(FaturasAberto::class.java)

        val lista = listOf(
            Mensagem("Gideone"),
            Mensagem("Thamiris"),
            Mensagem("Geraldina"),
            Mensagem("Ivani"),
            Mensagem("Gideone"),
            Mensagem("Thamiris"),
            Mensagem("Geraldina"),
            Mensagem("Ivani"),

        )

        rvLista = findViewById(R.id.rv_lista)
        rvLista.adapter = MensagemAdapter(lista) // tipo: MesagemAdapter, Adapter
        rvLista.layoutManager = LinearLayoutManager(this)
    }

    private fun chamaApiFaturas(mensagem: Mensagem){
        val servico = serviceFaturas

    }
}