package com.example.projeto.activity.interfaces

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ServiceResetPassword {
    @POST("/api/login/reset_password.php")
    @FormUrlEncoded
    fun resetPassword(
        @Field("email") email: String):
            Call<String>

}