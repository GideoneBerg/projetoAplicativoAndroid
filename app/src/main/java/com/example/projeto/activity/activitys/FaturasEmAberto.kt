package com.example.projeto.activity.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.projeto.R
import com.example.projeto.activity.classes.MensagemAdapter

class FaturasEmAberto : AppCompatActivity() {

    private lateinit var rvLista: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faturas_em_aberto)

        val lista = listOf<String>(
            "Gideone",
            "Thamiris",
            "Geraldina",
            "Denison",
            "Deoclecio"

        )

        rvLista = findViewById(R.id.rv_lista)
        rvLista.adapter = MensagemAdapter(lista) // tipo: MesagemAdapter, Adapter
        rvLista.layoutManager = LinearLayoutManager(this)
    }
}