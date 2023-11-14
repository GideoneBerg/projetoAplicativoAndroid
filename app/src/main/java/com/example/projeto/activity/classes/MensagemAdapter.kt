package com.example.projeto.activity.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.projeto.R

class MensagemAdapter(
    private val lista: List<String>
) : Adapter<MensagemAdapter.MensagemViewHolder>(){

    inner class MensagemViewHolder(
        val itemView : View
    ) : ViewHolder(itemView){
        val textNome: TextView = itemView.findViewById(R.id.text_nome)
    }
    // Ao criar o View Holder -> cria a visualização
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensagemViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val itemView = layoutInflater.inflate(
            R.layout.layout_recycler_view, parent, false
        )
        return  MensagemViewHolder(itemView)
    }
    // Ao vincular o view holder = pega os dados e vincula na lista
    override fun onBindViewHolder(holder: MensagemViewHolder, position: Int) {

        val nome = lista[position]
        holder.textNome.text = nome


    }
    // getItemCount -> Recurerar a quantidade de itens
    override fun getItemCount(): Int {
        return lista.size
    }



}