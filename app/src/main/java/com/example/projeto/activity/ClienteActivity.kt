package com.example.projeto.activity

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto.R

import com.example.projeto.activity.webView.WebSpeedTestActivity
import com.example.projeto.databinding.ActivityClienteBinding

class ClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClienteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClienteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Ação dos botões
        botoesScroll()
        // Dados do cliente
        dadosAPI()

    }
     private fun dadosAPI() {

        // Trazendo dados para a activity
        val nomeUsuario = intent.getStringExtra("nomeUsuario")
        binding.textViewNome.text = nomeUsuario

        val plano = intent.getStringExtra("plano")
        binding.plano.text = plano

        val vencimento = intent.getStringExtra("vencimento")
        binding.vencimento.text = vencimento

         val cidade = intent.getStringExtra("cidade")

         val rua = intent.getStringExtra("rua")



         // Botao criado para exibição de dados do cliente usando popup

         binding.maisDados.setOnClickListener(){
             val dialog = Dialog(this@ClienteActivity)
             dialog.setContentView(R.layout.popup)

             val nome = dialog.findViewById<TextView>(R.id.nomeCliente)
             nome.text = nomeUsuario

             val ruaCliente = dialog.findViewById<TextView>(R.id.ruaCliente)
             ruaCliente.text = rua

             val cidadeAtual = dialog.findViewById<TextView>(R.id.cidadeCliente)
             cidadeAtual.text = cidade

//             val buttonFechar = dialog.findViewById<Button>(R.id.buttonFechar)
//             buttonFechar.setOnClickListener {
//                 dialog.dismiss() // Fecha o diálogo quando o botão "Fechar" é clicado
//             }
             dialog.show()


         }

    }


    private fun botoesScroll(){

        this.binding.siteApcTecnologia.setOnClickListener{
            val site = "http://arteempc.com.br:6565/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(site))
            startActivity(intent)
        }

        this.binding.whatsappApcTecnologia.setOnClickListener {
            val whatsapp = "https://api.whatsapp.com/send/?phone=5581986271986&text&type=phone_number&app_absent=0"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(whatsapp))
            startActivity(intent)
        }

        this.binding.facebookApcTecnologia.setOnClickListener {
            val facebook = "https://www.facebook.com/ARTEEMPC?mibextid=ZbWKwL"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebook))
            startActivity(intent)
        }

        this.binding.instagramApcTecnologia.setOnClickListener{
            val instagram = "https://www.instagram.com/arteempc/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagram))
            startActivity(intent)
        }
        // Scroll
        this.binding.centralClienteScroll.setOnClickListener{
            val telefone = "8134356078"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data =Uri.parse("tel:$telefone")
            startActivity(intent)
        }

        binding.testeVelocidade.setOnClickListener {
            val intent = Intent(this, WebSpeedTestActivity::class.java)
            startActivity(intent)
        }
    }


}