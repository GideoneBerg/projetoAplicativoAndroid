package com.example.projeto.activity.interfaces

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ServiceNewPassword {

    @FormUrlEncoded
//    @POST("/login/new_password.php")
    @POST("/api/new_password.php") // nova
    fun submitData(
        @Field("email") email: String,
        @Field("otp") otp: String,
        @Field("new-password") newPassword: String
    ): Call<String>

}