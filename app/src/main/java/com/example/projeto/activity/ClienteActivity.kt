package com.example.projeto.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
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
        val plano = intent.getStringExtra("plano")
        val vencimento = intent.getStringExtra("vencimento")

        val textViewNome = findViewById<TextView>(R.id.textViewNome)
        textViewNome.text = nomeUsuario

        val textViewPlano = findViewById<TextView>(R.id.plano)
        textViewPlano.text = plano

        val textViewVencimento = findViewById<TextView>(R.id.vencimento)
        textViewVencimento.text = vencimento
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
        val testeInternet = findViewById<Button>(R.id.teste_velocidade)
        testeInternet.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, WebSpeedTestActivity::class.java)
            startActivity(intent)
        })
    }
}