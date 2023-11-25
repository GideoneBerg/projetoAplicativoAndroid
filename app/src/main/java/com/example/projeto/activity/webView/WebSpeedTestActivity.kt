package com.example.projeto.activity.webView

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.webkit.CookieManager
import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.projeto.R
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
            val string = "https://arteempc.com/boleto/boleto.hhvm?titulo="+fatura+"&contrato="+login+""
            val webView = binding.webView
            val scale = resources.displayMetrics.widthPixels / 8
            webView.setInitialScale(scale)
            val scale2 = resources.displayMetrics.heightPixels / 16
            webView.setInitialScale(scale2)

            // Habilitar zoom na WebView
            webView.settings.setSupportZoom(true)
            webView.settings.builtInZoomControls = true
            webView.settings.displayZoomControls = false

            webView.webViewClient = WebViewClient()
            webView.loadUrl(string)
            webView.settings.javaScriptEnabled =  true
            webView.settings.domStorageEnabled = true

            val botaoFatura = findViewById<Button>(R.id.btnDownFatura)
            botaoFatura.visibility = View.VISIBLE
            botaoFatura.setOnClickListener {
                //realizarDownload()

                val url = webView.url
                val fileName = "boleto" // Ajuste isso para o nome desejado do arquivo com a extensão .pdf
                val request = DownloadManager.Request(Uri.parse(url))

                //------------------------COOKIE!!------------------------
                val cookies = CookieManager.getInstance().getCookie(url)
                request.addRequestHeader("cookie", cookies)
                //------------------------COOKIE!!------------------------

                // Defina o tipo de MIME para PDF
                request.setMimeType("application/PDF")
                request.addRequestHeader("User-Agent", webView.settings.userAgentString)
                request.setDescription("Download do arquivo PDF")
                request.setTitle(fileName)
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

                val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                dm.enqueue(request)
                Toast.makeText(applicationContext, "Iniciando Download do PDF", Toast.LENGTH_LONG).show()


            }




        } else {
            val webView = binding.webView
            webView.webViewClient = WebViewClient()
            webView.loadUrl("https://www.testeavelocidade.net/speedtest-claro/")
            webView.settings?.javaScriptEnabled =  true
            webView.settings?.domStorageEnabled = true
        }
    }

    private fun realizarDownload() {


    }
}