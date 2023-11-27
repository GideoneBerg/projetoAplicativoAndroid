package com.example.projeto.activity.classes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.ParseException
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.R
import java.text.SimpleDateFormat
import java.util.Locale

class FaturaAdapterPagas(
    private val faturasListPg: List<Lancamento>
) : RecyclerView.Adapter<FaturaAdapterPagas.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textValor: TextView = itemView.findViewById(R.id.valor)
        val textVencimento: TextView = itemView.findViewById(R.id.vencimento)
        val textStatusFatura: TextView = itemView.findViewById(R.id.statusFatura)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflar o layout do item da fatura e criar uma instância do ViewHolder
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_recycler_view_pago, parent, false)
        return ViewHolder(view)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Atualize os elementos de layout com os dados da fatura
        val lancamentoPagas = faturasListPg.getOrNull(position)

        holder.textValor.text = "R$ ${lancamentoPagas?.valor}"
        val statusFormat = lancamentoPagas?.status
        holder.textStatusFatura.text = statusFormat?.uppercase()
        val dataVencimento = lancamentoPagas?.datavenc?.let { formatarData(it) }
        holder.textVencimento.text = "Vence em $dataVencimento"
    }
    override fun getItemCount(): Int {

        return faturasListPg.size
    }

    private fun formatarData(data: String): String {
        val formatoBanco = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return try {
            val dataFormatada: String = formatoBrasileiro.format(formatoBanco.parse(data))
            dataFormatada
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }


}