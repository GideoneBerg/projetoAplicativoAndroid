package com.example.projeto.activity.activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto.R
import com.example.projeto.activity.classes.ResetPassword
import com.example.projeto.activity.classes.RetrofitService
import com.example.projeto.activity.interfaces.ServiceResetPassword
import com.example.projeto.databinding.ActivityRedefinirSenhaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RedefinirSenhaActivity : AppCompatActivity() {

    private lateinit var serviceResetPassword: ServiceResetPassword
    private lateinit var binding: ActivityRedefinirSenhaBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRedefinirSenhaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Serviço Retrofit(Conexão com banco de dados)
        serviceResetPassword = RetrofitService.getRetrofitInstance()
            .create(ServiceResetPassword::class.java)

        val editText = findViewById<EditText>(R.id.email)
        val button = findViewById<Button>(R.id.btnToken)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        button.setOnClickListener(View.OnClickListener {
            val email = editText.text.toString()
            if (!isValidEmail(email)) {
                // Exibir uma mensagem de erro ao usuário
                editText.error = "Email inválido"
                return@OnClickListener
            }
            progressBar.visibility = View.VISIBLE
            button.visibility = View.INVISIBLE

            val servico = serviceResetPassword

            servico.resetPassword(email).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result == "success") {
                            Toast.makeText(applicationContext, "Email Enviado! Verifique Seu Email" , Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, NovaSenhaActivity::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                            button.visibility = View.VISIBLE
                        }
                    } else {
                        Toast.makeText(applicationContext, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                        button.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    button.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    Toast.makeText(applicationContext, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        })
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    companion object {
        // mascara do email
        private const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}"
    }
}