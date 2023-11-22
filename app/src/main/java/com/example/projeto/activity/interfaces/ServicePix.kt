package com.example.projeto.activity.interfaces


import com.example.projeto.activity.classes.Pix
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ServicePix {
    @FormUrlEncoded
    @POST("/api/lancamentos_pix.php")
    fun getPix(
        @Field("uuid_lanc") uuidLanc: String
    ): Call <List<Pix>>
}