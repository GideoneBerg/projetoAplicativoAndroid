package com.example.projeto.activity.activitys

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.R
import com.example.projeto.activity.classes.FaturasAdapterVencidas
import com.example.projeto.activity.classes.Lancamento
import com.example.projeto.databinding.ActivityFaturasVencidasBinding

class FaturasVencidas : AppCompatActivity() {

    private var lancamentosVencidos : List<Lancamento> = emptyList()
    private lateinit var rvLista3: RecyclerView
    private lateinit var faturasAdapter: FaturasAdapterVencidas


    private val binding by lazy {
        ActivityFaturasVencidasBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lancamentosVencidos = intent.getParcelableArrayListExtra("lancamentosVencidos", Lancamento::class.java)?: emptyList()


        if(lancamentosVencidos.isNotEmpty()){
            rvLista3 = findViewById(R.id.rv_lista3)
            faturasAdapter = FaturasAdapterVencidas(lancamentosVencidos)
            rvLista3.adapter = faturasAdapter
            rvLista3.layoutManager = LinearLayoutManager(this)

        }else{
            binding.mensagemTextView.visibility = View.VISIBLE
            binding.rvLista3.visibility = View.GONE

        }

    }
}