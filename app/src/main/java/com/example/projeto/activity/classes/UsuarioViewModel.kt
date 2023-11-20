package com.example.projeto.activity.classes

import Usuario
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UsuarioViewModel: ViewModel() {

    private val usuarioLiveData = MutableLiveData<Usuario>()
    fun setUsuario(usuario: Usuario) {
        usuarioLiveData.value = usuario
    }

    fun getUsuario():LiveData<Usuario>{
        return usuarioLiveData
    }


}