package com.example.projeto.activity.classes

import android.content.Context
import com.example.projeto.R

class ShowToastAlert(context: Context) : ShowToast(context, R.layout.toast_custom_alert) {

    override fun showToast(message: String) {
        super.showToast(message)
        // Adicionar código adicional aqui, se necessário
    }
}
