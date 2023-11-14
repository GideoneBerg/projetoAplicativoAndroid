package com.example.projeto.activity.interfaces

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ServiceClientRequest {
    @FormUrlEncoded
    @POST("/api/solicitacao_servico.php")
    fun enviarDados(
        @Field("cliente_cod") cod: String,
        @Field("nome_cliente") nomeUsuario: String,
        @Field("motivo_chamado") motivo: String,
        @Field("descricao") descricao: String
    ): Call<String>
}