package com.example.projeto.activity.activitys
import Usuario
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projeto.R
import com.example.projeto.databinding.ActivitySolicitacaoClienteBinding
import com.google.android.material.snackbar.Snackbar

class SolicitacaoCliente : AppCompatActivity() {

    private lateinit var binding: ActivitySolicitacaoClienteBinding
    private var nome: String? = null // Declare a variável globalmente
     private var cpf: String? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolicitacaoClienteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Obtenha os dados do usuário do Intent
        nome = intent.getStringExtra("nome")
        cpf = intent.getStringExtra("cpf")


        if (nome != null && cpf != null){
            // Exemplo de como usar os dados do usuário na sua atividade
            binding.textViewNome.text = nome
            binding.textViewCpf.text = cpf
            // Não temos uma visualização para o CPF, mas você pode incluir conforme necessário
            binding.botaoEnvSolicitacao.setOnClickListener {
                abrirLinkWhatsApp()
            }

        }

    }

    private fun abrirLinkWhatsApp() {
        val numeroTelefone = "5581986271986" // Substitua pelo número desejado

        // Dados do usuário
        val nomeUsuario = nome
        val cpfUsuario = cpf

        // Mensagem do usuário a ser digitada
        val mensagemUsuario = binding.descricao.text.toString()
        val mensagemFinal = "*Preciso de Suporte Técnico*\n\nNome: $nomeUsuario\nCPF: $cpfUsuario\n\n$mensagemUsuario"

        // Construa o URI com o link do WhatsApp
        val uri = Uri.parse("https://api.whatsapp.com/send/?phone=$numeroTelefone&text=$mensagemFinal&type=phone_number&app_absent=0")

        // Cria um Intent com a ação ACTION_VIEW e o URI do WhatsApp
        val intent = Intent(Intent.ACTION_VIEW, uri)

        // Verifica se há aplicativos que podem lidar com a Intent
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {

            snackBar("Não há aplicativos para abrir o WhatsApp.")
        }
    }


    private fun snackBar(mensagem: String) {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            mensagem,
            Snackbar.LENGTH_LONG
        )
        if (mensagem == "Solicitação Enviada") {
            snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.azulAnil))
        } else {
            snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.rosa))
        }
        snackbar.show()
    }
}

