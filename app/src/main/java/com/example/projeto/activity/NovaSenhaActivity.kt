package com.example.projeto.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto.R
import com.example.projeto.databinding.ActivityNovaSenhaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class NovaSenhaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNovaSenhaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaSenhaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dadosAPI()

        val email = intent.extras!!.getString("email")

        val editTextNewPassword = findViewById<EditText>(R.id.newpassword)
        val editTextOPT = findViewById<EditText>(R.id.otp)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val button = findViewById<Button>(R.id.btnEnviar)
        button.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.31.75/Login/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)
            val call = apiService.submitData(email!!, editTextOPT.text.toString(), editTextNewPassword.text.toString())
            call.enqueue(object : Callback<String> {
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
                            Toast.makeText(applicationContext, responseBody, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Erro na requisição", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(applicationContext, "Erro na requisição", Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                }
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun dadosAPI() {
        val extras = intent.extras ?: return

        val linkEmail = extras.getString("email")

        binding.linkemail.text = "Verifique seu E-mail $linkEmail"
    }

    interface ApiService {
        @FormUrlEncoded
        @POST("new_password.php")
        fun submitData(
            @Field("email") email: String,
            @Field("otp") otp: String,
            @Field("new-password") newPassword: String
        ): Call<String>
    }
}