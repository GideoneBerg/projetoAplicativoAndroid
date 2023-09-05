package com.example.projeto.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto.activity.classes.Usuario
import com.example.projeto.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl("http://192.168.31.75/")
    .build()
    .create(LoginActivity.EnviaUsuario::class.java)
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        funcaoBotoes()
    }
    private fun funcaoBotoes() {
        binding.buttonAjuda.setOnClickListener {
            val url = "https://api.whatsapp.com/send/?phone=5581986271986&text&type=phone_number&app_absent=0"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        binding.btnEntrar.setOnClickListener {
            // isDone verifica se o usuario preencheu todos os campos
            // unMasked recupera os dados sem a máscara
            val isDone = binding.editTextCpf.isDone
            if (isDone){ // verifica se o usuario digitou os dados corretamente
                val usuario = Usuario()
                usuario.cpf = binding.editTextCpf.unMasked
                usuario.senha = binding.editTextSenha.text.toString()
                chamaAPI(usuario)
            } else {
                Toast.makeText(this,"Ops!, Preencha um CPF válido.", Toast.LENGTH_SHORT).show()
                }
        }
    }
    private fun chamaAPI(usuario: Usuario) {
        retrofit.setUsuario(usuario.cpf, usuario.senha).enqueue(object :
            Callback<Usuario> {
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Log.d("Erro", t.toString())
            }

            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (response.body()!!.cpf == "vazio") {
                            exibeToast(false)
                        } else {
                            exibeToast(true)
                            val nomeUsuario = response.body()?.nome
                            val plano = response.body()?.plano
                            val vencimento = response.body()?.vencimento
                            val cidade = response.body()?.cidade
                            val rua = response.body()?.rua
                            val intent = Intent(this@LoginActivity, ClienteActivity::class.java)
                            intent.putExtra("nomeUsuario", nomeUsuario)
                            intent.putExtra("plano", plano)
                            intent.putExtra("vencimento",vencimento)
                            intent.putExtra("cidade", cidade)
                            intent.putExtra("rua", rua)
                            startActivity(intent)
                            finish()
                            // Limpa os campos depois de efetuar o login
                            limpaCampos()
                        }
                    }
                }
            }
        })
    }

    private fun exibeToast(respostaServidor: Boolean) {

        if (respostaServidor) {
            Toast.makeText(this, "Usuário autenticado", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Usuário ou senha incorretos", Toast.LENGTH_LONG).show()
        }
    }

    private fun limpaCampos() {
        binding.editTextCpf.setText("")
        binding.editTextSenha.setText("")
    }

    interface EnviaUsuario {
        @FormUrlEncoded
        @POST("/Login/login.php")
        fun setUsuario(
            @Field("cpf") cpf: String,
            @Field("senha") senha: String,

            ): Call<Usuario>

    }
    
}