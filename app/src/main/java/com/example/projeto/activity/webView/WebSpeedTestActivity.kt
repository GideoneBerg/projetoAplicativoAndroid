package com.example.projeto.activity.webView

import Usuario
import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.example.projeto.activity.classes.Lancamento
import com.example.projeto.activity.model.DadosSingleton
import com.example.projeto.databinding.ActivityWebSpeedTestBinding

class WebSpeedTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebSpeedTestBinding
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebSpeedTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fatura = intent.getStringExtra("titulo")
        if (fatura != null){

            val login = DadosSingleton.usuario?.login
            val titulo = fatura

            val string = "https://arteempc.com/boleto/boleto.hhvm?titulo="+fatura+"&contrato="+login+""

            val webView = binding.webView
            webView.webViewClient = WebViewClient() // acessar o site sem sair do app
            webView.loadUrl(string)
            webView.settings.javaScriptEnabled =  true // ativando o javascript
            webView.settings.domStorageEnabled = true // aumentar desempenho do WebView
        } else {

            val webView = binding.webView
            webView.webViewClient = WebViewClient() // acessar o site sem sair do app
            webView.loadUrl("https://www.testeavelocidade.net/speedtest-claro/")
            webView.settings.javaScriptEnabled =  true // ativando o javascript
            webView.settings.domStorageEnabled = true // aumentar desempenho do WebView

        }


    }
}