package com.example.projeto.activity.activitys


import Usuario
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
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
import com.example.projeto.activity.classes.Lancamento
import com.example.projeto.activity.classes.Pix
import com.example.projeto.activity.classes.QRCodeData
import com.example.projeto.activity.interfaces.ServiceLancamentos
import com.example.projeto.activity.model.RetrofitService
import com.example.projeto.activity.interfaces.ServiceLogin
import com.example.projeto.activity.interfaces.ServicePix
import com.example.projeto.activity.model.NetworkUtils
import com.example.projeto.databinding.ActivityEscolhaPagamentoBinding
import com.example.projeto.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var serviceLogin: ServiceLogin
    private lateinit var serviceLancamentos: ServiceLancamentos
    private val lancamentosVencidos = mutableListOf<Lancamento>()
    private val lancamentosPagos = mutableListOf<Lancamento>()
    private val lancamentosAbertos = mutableListOf<Lancamento>()
    private var lancamentos: List<Lancamento> = emptyList()

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkNetworkAndInitialize()

        funcaoBotoes()

    }


    private fun checkNetworkAndInitialize() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            // Se a rede estiver disponível, execute suas ações aqui
            serviceLogin = RetrofitService.getRetrofitInstance().create(ServiceLogin::class.java)
            serviceLancamentos = RetrofitService.getRetrofitInstance().create(ServiceLancamentos::class.java)


        } else {
            showNoInternetSnackbar()
        }
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

        binding.btnEntrar.setOnClickListener {
            loginUsuario()
        }

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

    }

    private fun loginUsuario() {

            // isDone verifica se o usuario preencheu todos os campos
            // unMasked recupera os dados sem a máscara
            val isDone = binding.editTextCpfCnpj.isDone
            if (isDone) { // verifica se o usuario digitou os dados corretamente
                val cpf = binding.editTextCpfCnpj.unMasked
                val senha = binding.editTextSenha.text.toString()
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

                            if (usuario?.cpf == "vazio") {

                                exibeSnackBar(false)
                            } else {

                                if (usuario != null) {
                                    usuario.login?.let { chamadaLancamentos(it) }

                                }
                                binding.progressBar2.visibility = View.VISIBLE
                                binding.btnEntrar.visibility = View.INVISIBLE

                                exibeSnackBar(true)
                                Handler(Looper.getMainLooper()).postDelayed({
                                    val intent = Intent(this@LoginActivity, ClienteActivity::class.java)
                                    intent.putExtra("usuario", usuario)

                                    intent.putExtra("lancamentosVencidos", ArrayList(lancamentosVencidos))
                                    intent.putExtra("lancamentosPagos", ArrayList(lancamentosPagos))
                                    intent.putExtra("lancamentosAbertos", ArrayList(lancamentosAbertos))



                                   /// intent.putParcelableArrayListExtra("lancamentos", ArrayList(lancamentos))
                                    startActivity(intent)
                                    finish()
                                }, 2000)

                                // Dados que seram enviados para ClienteActivity
                                limpaCampos()
                            }
                        }
                    }
                })
            } else {
                snackBar("Ops! Campos vazios")
            }

    }
    private fun chamadaLancamentos(login: String){

        serviceLancamentos.getLancamentos(login).enqueue(object :
            Callback<List<Lancamento>> {
            override fun onFailure(call: Call<List<Lancamento>>, t: Throwable) {
                // registra informações de erro
                Log.d("Erro", t.toString())
                snackBar("Tivemos um problema. Tente novamente mais tarde.")

            }
            override fun onResponse(call: Call<List<Lancamento>>, response: Response<List<Lancamento>>) {
                if (response.isSuccessful) {
                    lancamentos = response.body()!!
                    if (lancamentos.isNullOrEmpty()) {
                        // Lista de lançamentos está vazia ou nula
                    } else {


                        lancamentos.forEach {
                            when (it.status?.lowercase()) {
                                "vencido" -> lancamentosVencidos.add(it)
                                "pago" -> lancamentosPagos.add(it)
                                else -> lancamentosAbertos.add(it)
                            }
                        }



                    }
                }
            }
        })

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
            Snackbar.LENGTH_INDEFINITE
        ).setBackgroundTint(ContextCompat.getColor(this, R.color.rosa))

        snackbar.setAction("Abrir Configurações", View.OnClickListener {
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            startActivity(intent)
        })
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.amarelo))

        snackbar.show()
    }
    override fun onResume() {
        super.onResume()
        checkNetworkAndInitialize()
    }

}