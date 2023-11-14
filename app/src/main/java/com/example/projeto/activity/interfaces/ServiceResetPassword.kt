package com.example.projeto.activity.interfaces

import com.example.projeto.activity.classes.ResetPassword
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ServiceResetPassword {
//    @POST("/login/reset_password.php")
    @POST("/api/login/reset_password.php") // antiga
 //   @POST("/api/reset_password.php") // nova
    @FormUrlEncoded
    fun resetPassword(
        @Field("email") email: String):
            Call<String>

}