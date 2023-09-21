package com.example.projeto.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projeto.R
import java.util.regex.Pattern

class RedefinirSenhaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redefinir_senha)
        val editText = findViewById<EditText>(R.id.email)
        val button = findViewById<Button>(R.id.btnEnviar)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        button.setOnClickListener(View.OnClickListener {
            val email = editText.text.toString()
            if (!isValidEmail(email)) {
                // Exibir uma mensagem de erro ao usuário
                editText.error = "Email inválido"
                return@OnClickListener
            }
            progressBar.visibility = View.VISIBLE
            val queue = Volley.newRequestQueue(applicationContext)
            val url = "http://192.168.31.75/Login/reset_password.php"
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener<String> { response ->
                    progressBar.visibility = View.GONE
                    if (response == "success") {
                        val intent = Intent(applicationContext, NovaSenhaActivity::class.java)
                        intent.putExtra("email", editText.text.toString())
                        startActivity(intent)
                        finish()
                    } else Toast.makeText(applicationContext, response, Toast.LENGTH_SHORT).show()
                }, Response.ErrorListener { error ->
                    progressBar.visibility = View.GONE
                    error.printStackTrace()
                }) {
                override fun getParams(): Map<String, String>? {
                    val paramV: MutableMap<String, String> = HashMap()
                    paramV["email"] = editText.text.toString()
                    return paramV
                }
            }
            // definindo um tempo para verificar o email
            stringRequest.retryPolicy = object : RetryPolicy {
                override fun getCurrentTimeout(): Int {
                    return 30000
                }

                override fun getCurrentRetryCount(): Int {
                    return 30000
                }

                @Throws(VolleyError::class)
                override fun retry(error: VolleyError) {
                }
            }
            queue.add(stringRequest)
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