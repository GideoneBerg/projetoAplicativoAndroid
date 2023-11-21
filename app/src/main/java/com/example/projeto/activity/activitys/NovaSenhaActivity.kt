package com.example.projeto.activity.activitys

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto.R
import com.example.projeto.activity.model.RetrofitService
import com.example.projeto.activity.interfaces.ServiceNewPassword
import com.example.projeto.databinding.ActivityNovaSenhaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovaSenhaActivity : AppCompatActivity() {

    private lateinit var serviceNewPassword: ServiceNewPassword
    private lateinit var binding: ActivityNovaSenhaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaSenhaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        serviceNewPassword = RetrofitService.getRetrofitInstance()
            .create(ServiceNewPassword::class.java)

        dadosAPI()

        binding.toggleButton.setOnCheckedChangeListener { _, _ ->
            // Chama a função para alternar a visibilidade da senha
            togglePasswordVisibility()
        }
        val button = findViewById<Button>(R.id.botaoToken)
        button.setOnClickListener {
            chamaAPI()
        }
    }
    private fun chamaAPI() {
        val textNewPassword = binding.novaSenha.text.toString()
        val progressBar = binding.progressBar
        val email = intent.extras!!.getString("email")
        val textOTP = binding.pinView.text.toString()

        progressBar.visibility = View.VISIBLE
        val servico = serviceNewPassword
        servico.submitData(email!!, textOTP, textNewPassword
        ).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody == "success") {
                            Toast.makeText(
                                applicationContext,
                                "Nova Senha Definida",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(applicationContext, responseBody, Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Erro na requisição", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(applicationContext, "Erro na requisição", Toast.LENGTH_SHORT)
                        .show()
                    t.printStackTrace()
                }
            })
    }
    private fun togglePasswordVisibility() {
        val editTextSenha = binding.novaSenha
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
    @SuppressLint("SetTextI18n")
    private fun dadosAPI() {
        val extras = intent.extras ?: return
        val linkEmail = extras.getString("email")
        binding.linkemail.text = linkEmail
    }
}