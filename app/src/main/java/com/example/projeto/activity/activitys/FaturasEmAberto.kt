package com.example.projeto.activity.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.R
import com.example.projeto.activity.classes.FaturasAdapter
import com.example.projeto.activity.classes.Lancamento
import com.example.projeto.activity.classes.RetrofitService
import com.example.projeto.activity.classes.Usuario
import com.example.projeto.activity.interfaces.ServiceLancamentos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


// Importe as bibliotecas necessárias

class FaturasEmAberto : AppCompatActivity() {
    private lateinit var serviceLancamentos: ServiceLancamentos
    private lateinit var rvLista: RecyclerView
    private lateinit var faturasAdapter: FaturasAdapter // Crie um adaptador para suas faturas
    //private var lancamentos: List<Lancamento> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faturas_em_aberto)


     //   lancamentos = (intent.getSerializableExtra("lancamentos") as? ArrayList<Lancamento>)!!
     //   rvLista = findViewById(R.id.rv_lista)

        // Inicialize o RecyclerView e o adaptador
      //  faturasAdapter = FaturasAdapter(lancamentos)
       // rvLista.adapter = faturasAdapter
      //  rvLista.layoutManager = LinearLayoutManager(this)
    }





}
