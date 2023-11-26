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
import androidx.core.net.ParseException
import com.example.projeto.R

import com.example.projeto.activity.classes.Lancamento
import com.example.projeto.activity.classes.Pix
import com.example.projeto.activity.classes.QRCodeData
import com.example.projeto.activity.model.RetrofitService

import com.example.projeto.activity.interfaces.ServicePix
import com.example.projeto.activity.model.DadosPix
import com.example.projeto.activity.model.DadosSingleton

import com.example.projeto.activity.webView.WebSpeedTestActivity
import com.example.projeto.databinding.ActivityClienteBinding
import com.example.projeto.databinding.ActivityEscolhaPagamentoBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import java.text.SimpleDateFormat
import java.util.Locale

class ClienteActivity : AppCompatActivity() {
    private lateinit var servicePix: ServicePix

    private var lancamentos: List<Lancamento> = emptyList()
    private var lancamentoPix: List<Pix> = emptyList()
    private val qrCodeDataList = mutableListOf<QRCodeData>()
    private var lancamentosAbertos = mutableListOf<Lancamento>()

    private val binding by lazy {
        ActivityClienteBinding.inflate(layoutInflater)
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //chamadas Pix
        servicePix = RetrofitService.getRetrofitInstance()
            .create(ServicePix::class.java)

        lancamentos = intent.getParcelableArrayListExtra("lancamentos", Lancamento::class.java)?: emptyList()

        //val lancamentosVencidos = intent.getSerializableExtra("lancamentosVencidos") as ArrayList<Lancamento>
        // val lancamentosPagos = intent.getSerializableExtra("lancamentosPagos") as ArrayList<Lancamento>
        lancamentosAbertos =
            (intent.getParcelableArrayListExtra("lancamentosAbertos", Lancamento::class.java)?: emptyList()).toMutableList()

        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            chamadaPix()

            faturaAtual()
        }
        // Ação dos botões
        botoesScroll()
        // Dados do cliente
        dadosAPI()

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun chamadaPix() {
       // lancamentos = intent.getParcelableArrayListExtra("lancamentos", Lancamento::class.java) ?: emptyList()
        val uuidLanc = mutableListOf<String>()

        lancamentosAbertos.forEach {
            it.uuid_lanc?.let { uuid ->
                uuidLanc.add(uuid)
            }
        }

        if (uuidLanc.isNotEmpty()) {
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {

                for (uuid in uuidLanc) {
                    try {
                        val response = withContext(Dispatchers.IO) {
                            servicePix.getPix(uuid).execute()
                        }

                        if (response.isSuccessful) {
                            lancamentoPix = response.body()!!
                            if (lancamentoPix.isNullOrEmpty()) {
                                Log.i("ErroPix", "Erro: $lancamentoPix")
                            }

                            lancamentoPix.forEach {
                                it.qrcode?.let { it1 -> QRCodeData(it1) }
                                    ?.let { it2 -> qrCodeDataList.add(it2) }
                            }
                            Log.i("Sucesso", " $lancamentoPix")
                        }
                    } catch (e: Exception) {
                        Log.e("Erro", e.toString())
                        withContext(Dispatchers.Main){
                            snackBar("Tivemos um problema. Tente novamente mais tarde.")
                        }

                    }
                }
            }
        }
    }
    private fun faturaAtual(){
        CoroutineScope(Dispatchers.IO).launch {
            val primeiroLancamento = lancamentosAbertos.firstOrNull()

            if (primeiroLancamento != null) {
                val primeiraDataVenc = primeiroLancamento.datavenc?.let { formatarData(it) }
                val valorPrimeiraFat = primeiroLancamento.valor

                withContext(Dispatchers.Main){
                    binding.vencimento.text = "Vence em $primeiraDataVenc"
                    binding.valor.text = "R$ $valorPrimeiraFat"
                    binding.statusFatura.text = primeiroLancamento.status?.uppercase()

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("SetTextI18n")
    private fun dadosAPI() {

       val usuario = intent.getParcelableExtra("usuario", Usuario::class.java)

        if(usuario != null) {

            DadosSingleton.usuario = Usuario(
                usuario.cpf,usuario.cpfCnpj,usuario.senha,
                usuario.nome, usuario.nascimento, usuario.plano,
                usuario.vencimento, usuario.cidade, usuario.rua,
                usuario.numero, usuario.bairro, usuario.estado,
                usuario.cod, usuario.mensagem, usuario.login
            )

            binding.plano.text = usuario.plano?.replace("_", " ")
           // binding.vencimento.text = usuario.vencimento
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun botoesScroll() {

        val extras = intent.extras ?: return

        binding.cardCliente.setOnClickListener {
            val intent = Intent(this, EscolhaPagamento::class.java)
            intent.putExtra("key", lancamentosAbertos.firstOrNull())
           // intent.putExtra("pixAtual", lancamentoPix.firstOrNull()?.qrcode)
            intent.putExtra("pixAtual", qrCodeDataList.firstOrNull()?.qrcode)
            startActivity(intent)
        }

        binding.financeiro.setOnClickListener {
            val intent = Intent(this@ClienteActivity, FaturasEmAberto::class.java)
            intent.putParcelableArrayListExtra("lancamentos", ArrayList(lancamentos))
            intent.putParcelableArrayListExtra("qrCode", ArrayList(qrCodeDataList))
            startActivity(intent)
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

    private fun formatarData(data: String): String {
        val formatoBanco = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return try {
            val dataFormatada: String = formatoBrasileiro.format(formatoBanco.parse(data))
            dataFormatada
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }


 //

}