package com.example.projeto.activity.activitys

import Usuario
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.projeto.R
import com.example.projeto.activity.model.RetrofitService
import com.example.projeto.activity.interfaces.ServiceFirstAccess
import com.example.projeto.databinding.ActivityPrimeiroAcessoBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrimeiroAcesso : AppCompatActivity() {

    private lateinit var serviceFirstAccess: ServiceFirstAccess
    private lateinit var binding: ActivityPrimeiroAcessoBinding
    private lateinit var cpf: String
    private lateinit var novaSenha: String
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
    private fun cadastroPrimeiroAcesso() {

        val isDone = binding.textCpfCnpj.isDone
        if (isDone) { // verifica se o usuario digitou os dados corretamente
            cpf = binding.textCpfCnpj.unMasked
            novaSenha = binding.txtNovaSenha.text.toString()
            consultaAPI()
        } else {
            snackBar("Ops! Campos vazios.")
        }
    }

    private fun snackBar(mensagem: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            mensagem,
            Snackbar.LENGTH_LONG
        ).setBackgroundTint(ContextCompat.getColor(this, R.color.azulAnil))
            .show()
    }

    private fun consultaAPI() {

        val servico = serviceFirstAccess
        servico.setCadastro(cpf, novaSenha).enqueue(object :
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
                                exibeSnackBar(mensagem)
                                lifecycleScope.launch(Dispatchers.Main) {
                                    delay(1000)

                                    val intent =
                                        Intent(this@PrimeiroAcesso, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            } else if (mensagem == "Senha ja cadastrada!") {
                                exibeSnackBar(mensagem)
                            } else if (mensagem == "CPF nao localizado!") {
                                exibeSnackBar(mensagem)
                            } else if (mensagem == "Senha nao pode estar em branco!") {
                                exibeSnackBar(mensagem)
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

    private fun exibeSnackBar(mensagem: String) {
        if (mensagem == "Senha Cadastrada com sucesso!") {
            Snackbar.make(
                findViewById(android.R.id.content),
                R.string.msg_primeiroAcesso1,
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(ContextCompat.getColor(this, R.color.azulAnil))
                .show()
        } else if (mensagem == "Senha ja cadastrada!") {
            Snackbar.make(
                findViewById(android.R.id.content),
                R.string.msg_primeiroAcesso2,
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(ContextCompat.getColor(this, R.color.alerta))
                .show()
        } else if (mensagem == "CPF nao localizado!") {
            Snackbar.make(
                findViewById(android.R.id.content),
                R.string.msg_primeiroAcesso3,
                Snackbar.LENGTH_LONG
            ).setBackgroundTint(ContextCompat.getColor(this, R.color.rosa))
                .show()
        } else if (mensagem == "Senha nao pode estar em branco!") {
            Snackbar.make(
                findViewById(android.R.id.content),
                R.string.msg_primeiroAcesso4,
                Snackbar.LENGTH_LONG
            ).setBackgroundTint(ContextCompat.getColor(this, R.color.rosa))
                .show()
        }
    }
}