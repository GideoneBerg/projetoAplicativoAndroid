package com.example.projeto.activity.model

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitService {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // Timeout de conexão
        .readTimeout(30, TimeUnit.SECONDS)    // Timeout de leitura
        .writeTimeout(30, TimeUnit.SECONDS)   // Timeout de escrita
        .build()

    private var retrofit: Retrofit? = null
    fun getRetrofitInstance(): Retrofit {
        if (retrofit == null){
            retrofit = Retrofit.Builder()
//                .baseUrl("http://192.168.31.196/")
                .baseUrl("https://arteempc.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }
        return retrofit!!
    }

}