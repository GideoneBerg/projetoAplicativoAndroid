package com.example.projeto.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projeto.R
import com.example.projeto.databinding.ActivityNovaSenhaBinding

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
            val queue = Volley.newRequestQueue(applicationContext)
            val url = "http://192.168.31.75/Login/new_password.php"
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener<String> { response ->
                    progressBar.visibility = View.GONE
                    if (response == "success") {
                        Toast.makeText(
                            applicationContext,
                            "Nova Senha Definida com Sucesso",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else Toast.makeText(applicationContext, response, Toast.LENGTH_SHORT).show()
                }, Response.ErrorListener { error ->
                    progressBar.visibility = View.GONE
                    error.printStackTrace()
                }) {
                override fun getParams(): Map<String, String>? {
                    val paramV: MutableMap<String, String> = HashMap()
                    paramV["email"] = email!!
                    paramV["otp"] = editTextOPT.text.toString()
                    paramV["new-password"] = editTextNewPassword.text.toString()
                    return paramV
                }
            }
            stringRequest.retryPolicy = DefaultRetryPolicy(
                5000,  // timeout em milissegundos
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            queue.add(stringRequest)
        }
    }

    private fun dadosAPI(){

        val extras = intent.extras ?: return

        val linkEmail = extras.getString("email")

        binding.linkemail.text = "Verifique seu E-mail $linkEmail"



    }

}