package com.example.projeto.activity.classes

import Usuario
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClienteViewModel: ViewModel() {
    private val clienteLiveData = MutableLiveData<Usuario>()

    fun setCliente(usuario: Usuario){
        clienteLiveData.value = usuario
    }

    fun getCliente(): LiveData<Usuario>{
        return  clienteLiveData
    }



}