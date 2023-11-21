package com.example.projeto.activity.activitys

import Usuario
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projeto.R

import com.example.projeto.activity.classes.Lancamento
import com.example.projeto.activity.model.RetrofitService
import com.example.projeto.activity.classes.UsuarioViewModel

import com.example.projeto.activity.interfaces.ServiceLancamentos
import com.example.projeto.activity.model.DadosSingleton
import com.example.projeto.activity.model.DadosSingleton.usuario
import com.example.projeto.activity.model.DataLancSingleton

import com.example.projeto.activity.webView.WebSpeedTestActivity
import com.example.projeto.databinding.ActivityClienteBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClienteActivity : AppCompatActivity() {

    private lateinit var serviceLancamentos: ServiceLancamentos
    private lateinit var binding: ActivityClienteBinding
    //private val usuario: Usuario? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClienteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        serviceLancamentos = RetrofitService.getRetrofitInstance()
            .create(ServiceLancamentos::class.java)




        // Ação dos botões
        botoesScroll()
        // Dados do cliente
        dadosAPI()
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("SetTextI18n")
    private fun dadosAPI() {

       val usuario = intent.getParcelableExtra("usuario", Usuario::class.java)
        if(usuario != null) {

            binding.plano.text = usuario.plano?.replace("_", " ")
            binding.vencimento.text = usuario.vencimento
            binding.textViewNome.text = usuario.nome

            // Botao criado para exibição de dados do cliente usando popup
            binding.maisDados.setOnClickListener {

                val builder = AlertDialog.Builder(this)
//             builder.setTitle("Dados do Cliente")

                val inflater = layoutInflater
                val dialogLayout = inflater.inflate(R.layout.popup, null)
                builder.setView(dialogLayout)

                val dialog = builder.create()
                // Personaliza o estilo do popup
                dialog.window?.setBackgroundDrawableResource(R.drawable.backgroud_popup)

                //  dados do cliente no popup
                val nome = dialogLayout.findViewById<TextView>(R.id.nome)
                nome.text = "Nome: ${usuario.nome}"

                val ruaCliente = dialogLayout.findViewById<TextView>(R.id.endereco)
                ruaCliente.text = "End: ${usuario.rua}, n° ${usuario.numero}"

                val cidadeAtual = dialogLayout.findViewById<TextView>(R.id.cidade)
                cidadeAtual.text = "Cidade: ${usuario.cidade}, ${usuario.estado}"

                val nasc = dialogLayout.findViewById<TextView>(R.id.nascimento)
                nasc.text = "Nascimento: ${usuario.nascimento}"

                val bairro = dialogLayout.findViewById<TextView>(R.id.bairro)
                bairro.text = "Bairro: ${usuario.bairro}"

                //Fechar o popup quando o botão "OK" é clicado

                val buttonOk = dialogLayout.findViewById<Button>(R.id.button_ok)
                buttonOk.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }
        }
    }

    private fun botoesScroll() {

        val extras = intent.extras ?: return

        val usuario = intent.getParcelableExtra<Usuario>("usuario")

        binding.financeiro.setOnClickListener {
            val login = usuario?.login
             serviceLancamentos.getLancamentos(login!!).enqueue(object :
                Callback<List<Lancamento>> {
                override fun onFailure(call: Call<List<Lancamento>>, t: Throwable) {
                    // registra informações de erro
                    Log.d("Erro", t.toString())
                          snackBar("Tivemos um problema. Tente novamente mais tarde.")

                }
                override fun onResponse(call: Call<List<Lancamento>>, response: Response<List<Lancamento>>) {
                    if (response.isSuccessful) {
                        val lancamentos = response.body()
                        if (lancamentos.isNullOrEmpty()) {

                        } else {
                            val intent = Intent(this@ClienteActivity, FaturasEmAberto::class.java)
                            intent.putParcelableArrayListExtra("lancamentos", ArrayList(lancamentos))
                            startActivity(intent)

                        }
                    }
                }
            })
        }

        binding.solicitarServico.setOnClickListener {
            val intent = Intent(this, SolicitacaoCliente::class.java)
            val cod = extras.getString("cod")
            val nomeUsuario = extras.getString("nome")
            intent.putExtra("cod", cod)
            intent.putExtra("nomeUsuario", nomeUsuario)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            realizarLogout()
        }

        binding.site.setOnClickListener {
            openUrl("https://arteempc.com/")
        }

        binding.whatsapp.setOnClickListener {
            openUrl("https://api.whatsapp.com/send/?phone=5581986271986&text&type=phone_number&app_absent=0")
        }

        binding.facebook.setOnClickListener {
            openUrl("https://www.facebook.com/ARTEEMPC?mibextid=ZbWKwL")
        }

        binding.instagram.setOnClickListener {
            openUrl("https://www.instagram.com/arteempc/")
        }
        // Scroll
        binding.centralCliente.setOnClickListener {
            val telefone = "8134356078"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$telefone")
            startActivity(intent)
        }

        binding.testeVelocidade.setOnClickListener {
            val intent = Intent(this, WebSpeedTestActivity::class.java)
            startActivity(intent)
        }

        binding.sobre.setOnClickListener {
            val intent = Intent(this, SobreActivity::class.java)
            startActivity(intent)
        }

        binding.segundaVia.setOnClickListener {
            val intent = Intent(this, PdfActivity::class.java)
            startActivity(intent)
        }
    }

    private fun realizarLogout() {

        val sharedPreferences = getSharedPreferences("db", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        snackBar("Logout realizado com sucesso")

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun snackBar(mensagem: String) {
        Snackbar.make(
            findViewById(R.id.layout_cliente),
            mensagem,
            Snackbar.LENGTH_LONG
        ).setBackgroundTint(ContextCompat.getColor(this, R.color.azulAnil))
            .show()
    }

    private fun exibeSnackBar(respostaServidor: Boolean) {
        if (respostaServidor) {
            Snackbar.make(
                findViewById(android.R.id.content),
                "ok",
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(ContextCompat.getColor(this, R.color.azulAnil))
                .show()

        } else {
            Snackbar.make(
                findViewById(android.R.id.content),
                "false",
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(ContextCompat.getColor(this, R.color.rosa))
                .show()
        }
    }



}