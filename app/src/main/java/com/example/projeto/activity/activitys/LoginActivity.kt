package com.example.projeto.activity.activitys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto.R
import com.example.projeto.activity.classes.Usuario
import com.example.projeto.databinding.ActivityLoginBinding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private fun servicoRetrofit(): EnviaUsuario {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Timeout de conexão
            .readTimeout(30, TimeUnit.SECONDS)    // Timeout de leitura
            .writeTimeout(30, TimeUnit.SECONDS)   // Timeout de escrita
            .build()

     return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
//          .baseUrl("http://10.0.2.2/") //virtual
            .baseUrl("http://192.168.31.75/") // casa
//            .baseUrl("https://arteempc.com/") // Servidor HTTPS
//            .baseUrl("http://192.168.1.101/") // ETE
            .client(okHttpClient)
            .build()

            .create(EnviaUsuario::class.java)
    }

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        funcaoBotoes()
    }


    private fun togglePasswordVisibility() {
        val editTextSenha = binding.editTextSenha
        if (editTextSenha.transformationMethod == PasswordTransformationMethod.getInstance()) {
            // Se a senha estiver oculta, mostra-a
            editTextSenha.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            // Se a senha estiver visível, oculta-a
            editTextSenha.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        // Move o cursor para o final do texto
        editTextSenha.setSelection(editTextSenha.text.length)
    }


    private fun funcaoBotoes() {
        binding.toggleButton.setOnCheckedChangeListener { _, _ ->
            // Chama a função para alternar a visibilidade da senha
            togglePasswordVisibility()
        }

        binding.textEsqueciSenha.setOnClickListener{
            val intent = Intent(this, RedefinirSenhaActivity::class.java)
            startActivity(intent)

        }

        binding.buttonAjuda.setOnClickListener {
            openUrl("https://api.whatsapp.com/send/?phone=5581986271986&text&type=phone_number&app_absent=0")
        }

        binding.btnEntrar.setOnClickListener {
            loginUsuario()
        }

    }

    private fun loginUsuario() {
        // isDone verifica se o usuario preencheu todos os campos
        // unMasked recupera os dados sem a máscara
        val isDone = binding.editTextCpf.isDone
        if (isDone) { // verifica se o usuario digitou os dados corretamente
            val usuario = Usuario()
            usuario.cpf = binding.editTextCpf.text.toString()
            usuario.senha = binding.editTextSenha.text.toString()
            chamaAPI(usuario)
        } else {
            Toast.makeText(this, "Ops!, Preencha um CPF válido.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun chamaAPI(usuario: Usuario) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar2)
        val botaoVisibilidade = findViewById<Button>(R.id.btnEntrar)

        val servico = servicoRetrofit()
        servico.setUsuario(usuario.cpf, usuario.senha).enqueue(object :
            Callback<Usuario> {
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                // registra informações de erro
                Log.d("Erro", t.toString())
            }
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.cpf == "vazio") {
                            progressBar.visibility = View.GONE
                            botaoVisibilidade.visibility = View.VISIBLE

                            exibeToast(false)
                        } else {
                            progressBar.visibility = View.VISIBLE
                            botaoVisibilidade.visibility = View.INVISIBLE
                            // Mensangem
                            exibeToast(true)

                            // Dados que seram enviados para ClienteActivity
                            dadosActivity(it)
                            // Limpa os campos depois de efetuar o login
                            limpaCampos()
                        }
                    }
                }

            }
        })
    }

    private fun dadosActivity(usuario: Usuario) {
        val intent = Intent(this@LoginActivity, ClienteActivity::class.java)
        intent.putExtra("nomeUsuario", usuario.nome)
        intent.putExtra("nascimento", usuario.nascimento)
        intent.putExtra("plano", usuario.plano)
        intent.putExtra("vencimento", usuario.vencimento)
        intent.putExtra("cidade", usuario.cidade)
        intent.putExtra("rua", usuario.rua)
        intent.putExtra("numero", usuario.numero)
        intent.putExtra("bairro", usuario.bairro)
        intent.putExtra("estado", usuario.estado)
        intent.putExtra("cod", usuario.cod)
        startActivity(intent)
        finish()
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
    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }


}