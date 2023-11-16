package com.example.projeto.activity.activitys


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.projeto.activity.classes.RetrofitService
import com.example.projeto.activity.classes.Usuario
import com.example.projeto.activity.interfaces.ServiceLogin
import com.example.projeto.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var handler: Handler
    private lateinit var serviceLogin: ServiceLogin
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        serviceLogin = RetrofitService.getRetrofitInstance()
            .create(ServiceLogin::class.java)

        sharedPreferences = getSharedPreferences("db", MODE_PRIVATE)
        handler = Handler(Looper.getMainLooper())
        startUserStatusTimer()

        funcaoBotoes()
    }

    private fun startUserStatusTimer() {
        handler.postDelayed({
            // Usuário inativo por 5 minutos (300,000 milissegundos)
            val inactivityThreshold = 300000
            val lastActiveTime = sharedPreferences.getLong("lastActiveTime", 0)

            if (System.currentTimeMillis() - lastActiveTime >= inactivityThreshold) {
                // Usuário inativo, realiza o logout
                logoutUser()
            } else {
                // Usuário ainda está ativo, continua o timer
                startUserStatusTimer()
            }
        }, 1000) // Executa a verificação a cada 1 segundo (pode ajustar conforme necessário)
    }

    private fun resetUserStatus() {
        // Atualiza o tempo de atividade do usuário
        val editor = sharedPreferences.edit()
        editor.putLong("lastActiveTime", System.currentTimeMillis())
        editor.apply()
    }

    private fun logoutUser() {
        // Implemente as ações necessárias para realizar o logout aqui
        // Por exemplo, limpe os dados do SharedPreferences e redirecione para a tela de login
        val editor = sharedPreferences.edit()
        editor.putBoolean("logado", false)
        editor.apply()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
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
        val usuario = Usuario()
        if (isNetworkAvailable()) {

            // isDone verifica se o usuario preencheu todos os campos
            // unMasked recupera os dados sem a máscara
            val isDone = binding.editTextCpfCnpj.isDone
            if (isDone) { // verifica se o usuario digitou os dados corretamente
                usuario.setCpf(binding.editTextCpfCnpj.unMasked)
                usuario.setSenha(binding.editTextSenha.text.toString())
                chamaAPI(usuario)
            } else {
                snackBar("Ops! Campos vazios")
            }
        } else {
            showNoInternetSnackbar()
        }

    }


    private fun chamaAPI(usuario: Usuario) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar2)
        val botaoVisibilidade = findViewById<Button>(R.id.btnEntrar)

        val servico = serviceLogin
        usuario.getCpf()?.let {
            servico.setUsuario(it, usuario.getSenha()!!).enqueue(object :
                Callback<Usuario> {
                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    // registra informações de erro
                    Log.d("Erro", t.toString())
                    snackBar("Tivemos um problema. Tente novamente mais tarde.")

                }

                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            //   val sharedPreferences = getSharedPreferences("db", MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putBoolean("logado", true)
                            editor.apply()

                            if (it.getCpf() == "vazio") {
                                progressBar.visibility = View.GONE
                                botaoVisibilidade.visibility = View.VISIBLE

                                exibeSnackBar(false)
                            } else {

                                // Mensangem
                                exibeSnackBar(true)
                                // Dados que seram enviados para ClienteActivity
                                dadosActivity(it)
                                progressBar.visibility = View.VISIBLE
                                botaoVisibilidade.visibility = View.INVISIBLE

                                // Limpa os campos depois de efetuar o login
                                limpaCampos()
                            }
                        }
                    }
                }
            })
        }
    }

    private fun dadosActivity(usuario: Usuario) {

        val intent = Intent(this@LoginActivity, ClienteActivity::class.java)
        intent.putExtra("nome", usuario.getNome())
        intent.putExtra("nascimento", usuario.getNascimento())
        intent.putExtra("plano", usuario.getPlano())
        intent.putExtra("vencimento", usuario.getVencimento())
        intent.putExtra("cidade", usuario.getCidade())
        intent.putExtra("rua", usuario.getRua())
        intent.putExtra("numero", usuario.getNumero())
        intent.putExtra("bairro", usuario.getBairro())
        intent.putExtra("estado", usuario.getEstado())
        intent.putExtra("login", usuario.login)
        intent.putExtra("cod", usuario.cod)

        // Exibe a Snackbar antes de iniciar a próxima atividade
        Snackbar.make(
            findViewById(android.R.id.content),
            "Usuário autenticado",
            Snackbar.LENGTH_SHORT
        ).setBackgroundTint(ContextCompat.getColor(this, R.color.azulAnil))
            .addCallback(object : Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    // A Snackbar foi dispensada (usuário a viu), então agora inicie a próxima atividade
                    startActivity(intent)
                    finish()

                    // Atualize os dados no SharedPreferences após iniciar a próxima atividade
                    val editor = sharedPreferences.edit()
                    editor.putString("nome", usuario.getNome())
                    editor.putString("nascimento", usuario.getNascimento())
                    editor.putString("plano", usuario.getPlano())
                    editor.putString("cidade", usuario.getCidade())
                    editor.putString("vencimento", usuario.getVencimento())
                    editor.putString("rua", usuario.getRua())
                    editor.putString("numero", usuario.getNumero())
                    editor.putString("bairro", usuario.getBairro())
                    editor.putString("estado", usuario.getEstado())
                    editor.apply()
                }
            }).show()
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

    override fun onResume() {
        super.onResume()
        resetUserStatus()
        startUserStatusTimer()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }

}