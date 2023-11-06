package com.example.projeto.activity.interfaces

import com.example.projeto.activity.classes.Usuario
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ServiceFirstAccess {
    @FormUrlEncoded
    @POST("/api/login/primeiro_acesso.php")
    fun setCadastro(
        @Field("cpf") cpf: String,
        @Field("senha_app") senha: String,
    ): Call<Usuario>

}