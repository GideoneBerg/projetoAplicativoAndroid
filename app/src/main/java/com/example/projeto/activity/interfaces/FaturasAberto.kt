package com.example.projeto.activity.interfaces

import com.example.projeto.activity.classes.Mensagem
import com.example.projeto.activity.classes.Usuario
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FaturasAberto {
    @FormUrlEncoded
//    @POST("/login/login.php")
    @GET("/api/login.php") // antiga
//    @POST("/api/login.php") // nova
    suspend fun getFaturas(): List<Mensagem>
}