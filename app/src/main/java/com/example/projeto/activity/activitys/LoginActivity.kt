package com.example.projeto.activity.activitys


import android.content.Intent

import android.net.Uri
import android.os.Bundle
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
        val usuario = Usuario()
        // isDone verifica se o usuario preencheu todos os campos
        // unMasked recupera os dados sem a máscara
        val cpf = binding.editTextCpfCnpj.unMasked.isEmpty()
        val senha = binding.editTextSenha.text.toString().isEmpty()
        //val isDone = binding.editTextCpfCnpj.isDone
        if (!cpf && !senha) { // verifica se o usuario digitou os dados corretamente
            usuario.setCpf(binding.editTextCpfCnpj.unMasked)
            usuario.setSenha(binding.editTextSenha.text.toString())
            chamaAPI(usuario)
        } else {
            snackBar("Ops! Campos vazios")
        }
    }


    private fun chamaAPI(usuario: Usuario) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar2)
        val botaoVisibilidade = findViewById<Button>(R.id.btnEntrar)

        val servico = serviceLogin
        servico.setUsuario(usuario.getCpf(), usuario.getSenha()).enqueue(object :
            Callback<Usuario> {
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                // registra informações de erro
                Log.d("Erro", t.toString())
            }

            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {

                    response.body()?.let {
                        val sharedPreferences = getSharedPreferences("db", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("logado", true)
                        editor.apply()

                        if (it.getCpf() == "vazio") {
                            progressBar.visibility = View.GONE
                            botaoVisibilidade.visibility = View.VISIBLE

                            exibeSnackBar(false)
                        } else {
                            progressBar.visibility = View.VISIBLE
                            botaoVisibilidade.visibility = View.INVISIBLE
                            // Mensangem
                            exibeSnackBar(true)
                            // Dados que seram enviados para ClienteActivity
                            dadosActivity(it)

                            // Limpa os campos depois de efetuar o login
                            limpaCampos()
                        }
                    }
                }
            }
        })
    }

    private fun dadosActivity(usuario: Usuario) {

        val intent = Intent(this@LoginActivity, ClienteActivity::class.java)
        intent.putExtra("nomeUsuario", usuario.getNome())
        intent.putExtra("nascimento", usuario.getNascimento())
        intent.putExtra("plano", usuario.getPlano())
        intent.putExtra("vencimento", usuario.getVencimento())
        intent.putExtra("cidade", usuario.getCidade())
        intent.putExtra("rua", usuario.getRua())
        intent.putExtra("numero", usuario.getNumero())
        intent.putExtra("bairro", usuario.getBairro())
        intent.putExtra("estado", usuario.getEstado())
        intent.putExtra("cod", usuario.cod)
        startActivity(intent)
        finish()

        val sharedPreferences = getSharedPreferences("db", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("nomeUsuario", usuario.getNome())
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

    private fun exibeSnackBar(respostaServidor: Boolean) {
        if (respostaServidor) {
            Snackbar.make(
                findViewById(R.id.pagina_login),
                "Usuário autenticado",
                Snackbar.LENGTH_LONG
            ).setBackgroundTint(ContextCompat.getColor(this, R.color.azulAnil))
                .show()

        } else {
            Snackbar.make(
                findViewById(R.id.pagina_login),
                "Usuário ou senha incorretos",
                Snackbar.LENGTH_LONG
            ).setBackgroundTint(ContextCompat.getColor(this, R.color.azulAnil))
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
        Snackbar.make(
            findViewById(R.id.pagina_login),
            mensagem,
            Snackbar.LENGTH_LONG
        ).setBackgroundTint(ContextCompat.getColor(this, R.color.azulAnil))
            .show()
    }
}