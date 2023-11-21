package com.example.projeto.activity.model

import android.widget.TextView
import com.example.projeto.activity.classes.Lancamento

object DataLancSingleton {

    fun pegarIndice(textViewTitulo: TextView
                    , textViewValor: TextView, textViewStatus: TextView,
                    textViewDataVenc: TextView){

        val lanc: ArrayList<Lancamento?>? = null


        if (lanc != null) {
            for (i in 0 until lanc.size){
                if (i == 0){
                    val lancamentoIndiceZero = lanc[i]
                    val valor = lancamentoIndiceZero?.valor
                    val titulo = lancamentoIndiceZero?.titulo
                    val status = lancamentoIndiceZero?.status
                    val datavenc = lancamentoIndiceZero?.datavenc

                    textViewTitulo.text = "Título: $titulo"
                    textViewValor.text = "Valor: $valor"
                    textViewStatus.text = "Status: $status"
                    textViewDataVenc.text = "Data de Vencimento: $datavenc"

                }


            }
        }





}


}