package com.example.projeto.activity.webView

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.projeto.databinding.ActivityWebSpeedTestBinding

class WebSpeedTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebSpeedTestBinding
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebSpeedTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webView = binding.webView
        webView.webViewClient = WebViewClient() // acessar o site sem sair do app
        webView.loadUrl("https://www.testeavelocidade.net/p/velocimetro-widget.html")
        webView.settings.javaScriptEnabled =  true // ativando o javascript
        webView.settings.domStorageEnabled = true // aumentar desempenho do WebView
    }
}