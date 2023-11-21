package com.example.projeto.activity.activitys


import Usuario
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projeto.R
import com.example.projeto.activity.model.RetrofitService
import com.example.projeto.activity.interfaces.ServiceLogin
import com.example.projeto.activity.model.DadosSingleton
import com.example.projeto.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var serviceLogin: ServiceLogin
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        serviceLogin = RetrofitService.getRetrofitInstance()
            .create(ServiceLogin::class.java)

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

        binding.txtPrimeiroAcesso.setOnClickListener {
            val intent = Intent(this, PrimeiroAcesso::class.java)
            startActivity(intent)
            finish()
        }

        binding.toggleButton.setOnCheckedChangeListener { _, _ ->
            // Chama a função para alternar a visibilidade da senha
            togglePasswordVisibility()
        }

        binding.textEsqueciSenha.setOnClickListener {
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

        if (isNetworkAvailable()) {

            // isDone verifica se o usuario preencheu todos os campos
            // unMasked recupera os dados sem a máscara
            val isDone = binding.editTextCpfCnpj.isDone
            if (isDone) { // verifica se o usuario digitou os dados corretamente
                val cpf = binding.editTextCpfCnpj.unMasked
                val senha = binding.editTextSenha.text.toString()

                val progressBar = findViewById<ProgressBar>(R.id.progressBar2)
                val botaoVisibilidade = findViewById<Button>(R.id.btnEntrar)

                val servico = serviceLogin
                servico.setUsuario(cpf, senha).enqueue(object :
                    Callback<Usuario> {
                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        // registra informações de erro
                        Log.d("Erro", t.toString())
                        snackBar("Tivemos um problema. Tente novamente mais tarde.")

                    }

                    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                        if (response.isSuccessful) {
                          val usuario = response.body()
                                  DadosSingleton.usuario = usuario
                                if (usuario?.cpf == "vazio") {
                                    progressBar.visibility = View.GONE
                                    botaoVisibilidade.visibility = View.VISIBLE

                                    exibeSnackBar(false)
                                } else {
                                    botaoVisibilidade.visibility = View.INVISIBLE
                                    progressBar.visibility = View.VISIBLE

                                    exibeSnackBar(true)
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        val intent = Intent(this@LoginActivity, ClienteActivity::class.java)
                                        intent.putExtra("usuario", usuario)
                                        startActivity(intent)
                                        finish()
                                    }, 1000)

                                    // Dados que seram enviados para ClienteActivity


                                    limpaCampos()
                                }
                        }
                    }
                })
            } else {
                snackBar("Ops! Campos vazios")
            }
        } else {
            showNoInternetSnackbar()
        }
    }
    private fun exibeSnackBar(respostaServidor: Boolean) {
        if (respostaServidor) {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Usuário autenticado",
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(ContextCompat.getColor(this, R.color.azulAnil))
                .show()

        } else {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Usuário ou senha incorretos",
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(ContextCompat.getColor(this, R.color.rosa))
                .show()
        }
    }
    private fun limpaCampos() {
        binding.editTextCpfCnpj.setText("")
        binding.editTextSenha.setText("")
    }
    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
    @SuppressLint("MissingPermission")
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }
    private fun snackBar(mensagem: String) {
        if (mensagem == "Ops! Campos vazios") {

            Snackbar.make(
                findViewById(android.R.id.content),
                mensagem,
                Snackbar.LENGTH_LONG
            ).setBackgroundTint(ContextCompat.getColor(this, R.color.alerta))
                .show()

        } else {
            Snackbar.make(
                findViewById(android.R.id.content),
                mensagem,
                Snackbar.LENGTH_LONG
            ).setBackgroundTint(ContextCompat.getColor(this, R.color.rosa))
                .show()
        }
    }
    private fun showNoInternetSnackbar() {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            R.string.conexao_internet,
            Snackbar.LENGTH_LONG
        ).setBackgroundTint(ContextCompat.getColor(this, R.color.rosa))

        snackbar.setAction("Abrir Configurações", View.OnClickListener {
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            startActivity(intent)
        })
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.amarelo))

        snackbar.show()
    }


}