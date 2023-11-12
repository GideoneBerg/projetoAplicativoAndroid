package com.example.projeto.activity.classes

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.example.projeto.R

open class ShowToast(private val context: Context, @LayoutRes private val layoutResId: Int) {

    open fun showToast(message: String) {
        // Crie o Toast com a mensagem
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        // Inflar o layout personalizado para o Toast
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val toastLayout = inflater.inflate(layoutResId, null)
        // Configurar o texto da mensagem no layout personalizado
        val textMessage = toastLayout.findViewById<TextView>(R.id.textMessage)
        textMessage.text = message
        // Definir o layout personalizado como a view do Toast
        toast.view = toastLayout

        // Mostrar o Toast
        toast.show()
    }
}
