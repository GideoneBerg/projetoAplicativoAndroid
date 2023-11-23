package com.example.projeto.activity.classes

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.R
import com.example.projeto.activity.activitys.EscolhaPagamento


class FaturasAdapter(private val faturasList: List<Lancamento> , private val faturasPix: List<QRCodeData>) :
    RecyclerView.Adapter<FaturasAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardview_recycler)
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
        val lancamento = faturasList.getOrNull(position)
        val lancamentoPix = faturasPix.getOrNull(position)


            // Configure os TextViews ou outros elementos de layout conforme necessário
        holder.cardView.setOnClickListener {

                val intent = Intent(holder.itemView.context, EscolhaPagamento::class.java).apply {
                    putExtra("key", lancamento)
                    putExtra("pix", lancamentoPix)
                }
                holder.itemView.context.startActivity(intent)

            }

            holder.textValor.text = "R$ ${lancamento?.valor}"
            holder.textVencimento.text = "Vence em ${lancamento?.datavenc}"
            holder.textStatusFatura.text = lancamento?.status


    }

    override fun getItemCount(): Int {

       // faturasPix.size

        // Retorne o número de faturas na lista
         return maxOf(faturasList.size, faturasPix.size)
    }

}


