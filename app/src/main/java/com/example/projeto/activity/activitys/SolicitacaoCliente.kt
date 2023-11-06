package com.example.projeto.activity.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.projeto.R
import com.example.projeto.activity.classes.RetrofitService
import com.example.projeto.activity.classes.Usuario
import com.example.projeto.activity.interfaces.ServiceClientRequest
import com.example.projeto.databinding.ActivitySolicitacaoClienteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class SolicitacaoCliente : AppCompatActivity() {

    private lateinit var serviceClientRequest: ServiceClientRequest

/*    private fun servicoRetrofit(): ServicoAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
//          .baseUrl("http://10.0.2.2/") //virtual
//            .baseUrl("http://192.168.31.75/") // casa
//            .baseUrl("https://arteempc.com/api/") // Servidor HTTPS
            .baseUrl("http://192.168.1.101/") // ET
            .build()
            .create(ServicoAPI::class.java)
    }*/

    private lateinit var binding: ActivitySolicitacaoClienteBinding
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolicitacaoClienteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Serviço Retrofit(Conexão com banco de dados)
        serviceClientRequest = RetrofitService.getRetrofitInstance()
            .create(ServiceClientRequest::class.java)

        this.spinner = binding.opcoes

        // Crie um ArrayAdapter usando o array de strings definido no XML
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.motivos,
            R.layout.lista_itens
        )

        // Especifique o layout a ser usado quando a lista de opções aparecer
        adapter.setDropDownViewResource(R.layout.lista_itens)
        // Associe o ArrayAdapter ao Spinner
        spinner.adapter = adapter

        // Defina o ouvinte de seleção do Spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                // Faça algo com o item selecionado, se necessário
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Executado quando nada é selecionado (opcional)
            }
        }
        binding.botaoEnvSolicitacao.setOnClickListener {
            val motivo = spinner.selectedItem.toString()
            val descricao = binding.descricao.text.toString()
            if (motivo.isNotEmpty() && descricao.isNotEmpty()){
                // enviando cod do cliente para o banco de dados

                val cod = intent.extras!!.getString("cod")
                val nomeUsuario = intent.extras!!.getString("nomeUsuario")

                val servico = serviceClientRequest
                servico.enviarDados(cod!!, nomeUsuario!!, motivo, descricao)
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

/*    interface ServicoAPI {
        @FormUrlEncoded
        @POST("/login/solicitacao_servico.php")
        fun enviarDados(
            @Field("cliente_cod") cod: String,
            @Field("nome_cliente") nomeUsuario: String,
            @Field("motivo_chamado") motivo: String,
            @Field("descricao") descricao: String
        ): Call<String>*/

}