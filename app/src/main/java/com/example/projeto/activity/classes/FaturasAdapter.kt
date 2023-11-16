package com.example.projeto.activity.classes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.R

class FaturasAdapter(private val faturasList: List<Lancamento>) :
    RecyclerView.Adapter<FaturasAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textValor: TextView = itemView.findViewById(R.id.valor)
        val textVencimento: TextView = itemView.findViewById(R.id.vencimento)
        val textStatusFatura: TextView = itemView.findViewById(R.id.statusFatura)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflar o layout do item da fatura e criar uma instância do ViewHolder
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_recycler_view, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Atualize os elementos de layout com os dados da fatura
        val lancamento = faturasList[position]
        // Configure os TextViews ou outros elementos de layout conforme necessário


        holder.textValor.text = "R$ ${lancamento.valor}"
        holder.textVencimento.text = "Vence em ${lancamento.datavenc}"
        holder.textStatusFatura.text = lancamento.status

    }

    override fun getItemCount(): Int {
        // Retorne o número de faturas na lista
        return faturasList.size
    }

//    fun atualizarFaturas(novaLista: List<Lancamento>) {
//        // Atualize a lista de faturas e notifique o RecyclerView
//        faturasList.clear()
//        faturasList.addAll(novaLista)
//        notifyDataSetChanged()
//    }

}


