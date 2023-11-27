package com.example.projeto.activity.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.ParseException
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.R
import java.text.SimpleDateFormat
import java.util.Locale

class  FaturasAdapterVencidas(private val faturasListvenc: List<Lancamento>
) : RecyclerView.Adapter<FaturasAdapterVencidas.ViewHolder>() {
    class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textValor: TextView = itemView.findViewById(R.id.valor)
        val textVencimento: TextView = itemView.findViewById(R.id.vencimento)
        val textStatusFatura: TextView = itemView.findViewById(R.id.statusFatura)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_recycler_view_vencido, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lancamentoVenc = faturasListvenc.getOrNull(position)

        holder.textValor.text = "R$ ${lancamentoVenc?.valor}"
        val statusFormat = lancamentoVenc?.status
        holder.textStatusFatura.text = statusFormat?.uppercase()
        val dataVencimento = lancamentoVenc?.datavenc?.let { formatarData(it) }
        holder.textVencimento.text = "Vencida em $dataVencimento"
    }

    override fun getItemCount(): Int {
        return faturasListvenc.size
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