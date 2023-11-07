package com.example.projeto.activity.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import com.example.projeto.activity.classes.RetrofitService
import com.example.projeto.activity.classes.Usuario
import com.example.projeto.activity.interfaces.ServiceFirstAccess
import com.example.projeto.databinding.ActivityPrimeiroAcessoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrimeiroAcesso : AppCompatActivity() {

    private  lateinit var serviceFirstAccess: ServiceFirstAccess
    private lateinit var binding: ActivityPrimeiroAcessoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrimeiroAcessoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Serviço Retrofit(Conexão com banco de dados)
        serviceFirstAccess = RetrofitService.getRetrofitInstance()
            .create(ServiceFirstAccess::class.java)

        funcaoBotoes()
    }
    private fun consultaAPI(usuario: Usuario) {

        val servico = serviceFirstAccess
        servico.setCadastro(usuario.cpf, usuario.senha).enqueue(object :
            Callback<Usuario> {
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                // registra informações de erro
                Log.d("Erro", t.toString())
            }

            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        val mensagem = result.mensagem
                        if (mensagem != null) {
                            if (mensagem == "Senha Cadastrada com sucesso!") {
                                exibeToast(mensagem)
                                val intent = Intent(this@PrimeiroAcesso, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else if (mensagem == "Senha ja cadastrada!") {
                                exibeToast(mensagem)
                            } else if (mensagem == "CPF nao localizado!") {
                                exibeToast(mensagem)
                            } else if (mensagem == "Senha nao pode estar em branco!") {
                                exibeToast(mensagem)
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Erro inesperado!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(applicationContext, "Null", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Erro na resposta da API",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })

    }
    private fun togglePasswordVisibility() {
        val txtNovaSenha = binding.txtNovaSenha
        if (txtNovaSenha.transformationMethod == PasswordTransformationMethod.getInstance()) {
            // Se a senha estiver oculta, mostra-a
            txtNovaSenha.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            // Se a senha estiver visível, oculta-a
            txtNovaSenha.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        // Move o cursor para o final do texto
        txtNovaSenha.setSelection(txtNovaSenha.text.length)
    }
    private fun funcaoBotoes() {

        binding.btnNovaSenha.setOnClickListener {
            cadastroPrimeiroAcesso()
        }
        binding.toggleButton.setOnCheckedChangeListener { _, _ ->
            // Chama a função para alternar a visibilidade da senha
            togglePasswordVisibility()
        }
    }
    private fun cadastroPrimeiroAcesso() {

        val isDone = binding.textCpfCnpj.isDone
        if (isDone) { // verifica se o usuario digitou os dados corretamente
            val usuario = Usuario()
            usuario.cpf = binding.textCpfCnpj.unMasked
            usuario.senha = binding.txtNovaSenha.text.toString()
            consultaAPI(usuario)
        } else {
            Toast.makeText(this, "Ops! Campos vazios.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun exibeToast(respostaServidor: String) {
        if (respostaServidor == "Senha Cadastrada com sucesso!") {
            val msg = "Senha cadastrada com sucesso!"
            Toast.makeText(this, msg , Toast.LENGTH_SHORT).show()
        } else if (respostaServidor == "Senha ja cadastrada!"){
            val msg = "Senha já cadastrada!"
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        } else if (respostaServidor == "CPF nao localizado!"){
            val msg = "Ops! CPF não localizado na base de dados."
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        } else if (respostaServidor == "Senha nao pode estar em branco!") {
            val msg = "Senha não pode estar em branco!"
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        }
    }
}