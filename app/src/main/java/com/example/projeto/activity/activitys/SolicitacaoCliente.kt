package com.example.projeto.activity.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projeto.databinding.ActivitySolicitacaoClienteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class SolicitacaoCliente : AppCompatActivity() {

    private fun servicoRetrofit(): ServicoAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
//          .baseUrl("http://10.0.2.2/") //virtual
            .baseUrl("http://192.168.31.75/") // casa
//            .baseUrl("http://192.168.100.181/") // ETE
            .build()
            .create(ServicoAPI::class.java)
    }

    private lateinit var binding: ActivitySolicitacaoClienteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolicitacaoClienteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.botaoEnvSolicitacao.setOnClickListener {
            val motivo = binding.motivo.text.toString()
            val descricao = binding.descricao.text.toString()
            if (motivo.isNotEmpty() && descricao.isNotEmpty()){
                // enviando cod do cliente para o banco de dados                                                                                                                                                                 20
                val cod = intent.extras!!.getString("cod")
                val servicoApi = servicoRetrofit()
                servicoApi.enviarDados(cod!!, motivo, descricao)
                    .enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()
                                if (responseBody == "success") {
                                    Toast.makeText(applicationContext, "Solicitação Enviada", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(applicationContext, responseBody, Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(applicationContext, "Erro: Solicitação não foi enviada", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Toast.makeText(applicationContext, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
            } else{
                Toast.makeText(this, "Ops! Os campos são obrigatórios", Toast.LENGTH_SHORT).show()
                }
            }
    }
    interface ServicoAPI {
        @FormUrlEncoded
        @POST("/Login/solicitacao_servico.php")
        fun enviarDados(
            @Field("cliente_cod") cod: String,
            @Field("motivo_chamado") motivo: String,
            @Field("descricao") descricao: String
        ): Call<String>
    }
}