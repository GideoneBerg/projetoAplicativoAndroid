package com.example.projeto.activity.activitys

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.ParseException
import com.example.projeto.R
import com.example.projeto.activity.classes.Lancamento
import com.example.projeto.activity.classes.Pix
import com.example.projeto.activity.classes.QRCodeData
import com.example.projeto.databinding.ActivityEscolhaPagamentoBinding
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EscolhaPagamento : AppCompatActivity() {


    private var lancamentoPix: QRCodeData? = null
    private val binding by lazy {
        ActivityEscolhaPagamentoBinding.inflate(layoutInflater)
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)




        val lancamento = intent.getParcelableExtra("key", Lancamento::class.java)
        val formatoBanco = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formatoDesejado = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        try {
            val dataBanco: Date? = lancamento?.datavenc?.let { formatoBanco.parse(it) }
            val dataFormatada: String? = dataBanco?.let { formatoDesejado.format(it) }
            binding.vencimento.text = "Vence em $dataFormatada"
        } catch (e: ParseException) {
            e.printStackTrace()
        }
       // val lancamentoPix = intent.getParcelableExtra("pix", Pix::class.java)
        if (lancamento != null) {
            val statusFormat = lancamento.status
            binding.statusFatura.text = statusFormat?.uppercase()
            binding.valor.text = lancamento.valor
            binding.codigo.text = lancamento.linhadig
        }
        gerarQRCode()

        val btnCopiarBoleto = binding.copiarCodBarras
        val codigo = binding.codigo.text.toString()

        btnCopiarBoleto.setOnClickListener {
            val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Chave do Boleto", codigo)
            clipBoard.setPrimaryClip(clip)
            CoroutineScope(Dispatchers.Main).launch {
                binding.copiarCodBarras.text = "Chave Copiada"
                delay(2000)
                binding.copiarCodBarras.text = "Copiar"
            }
        }
        val btnGerarPix = binding.btnGerarPix
        val codigoPix = binding.pixCopiaCola.text.toString()

        btnGerarPix.setOnClickListener {
            val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Chave Pix", codigoPix)
            clipBoard.setPrimaryClip(clip)
            CoroutineScope(Dispatchers.Main).launch {
                binding.btnGerarPix.text = "Pix Copiado"
                delay(2000)
                binding.btnGerarPix.text = "Copiar Pix"
            }
        }
    }

    private fun snackBar(mensagem: String) {

        Snackbar.make(
            findViewById(android.R.id.content),
            mensagem,
            Snackbar.LENGTH_SHORT
        ).setBackgroundTint(ContextCompat.getColor(this, R.color.azulAnil))
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun gerarQRCode() {
     lancamentoPix = intent.getParcelableExtra("pix", QRCodeData::class.java)

       // binding.pixCopiaCola.text = lancamentoPix?.qrcode

        val ivQRCode = binding.ivqrCode

        if (lancamentoPix != null) {
            val texto: String = lancamentoPix!!.qrcode
            val multiFormatWriter = MultiFormatWriter()
            try {
                val bitMatrix = multiFormatWriter.encode(texto, BarcodeFormat.QR_CODE, 600, 600)
                val width = bitMatrix.width
                val height = bitMatrix.height
                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                val color = ContextCompat.getColor(this, R.color.azulMarinho)
                for (x in 0 until width) {
                    for (y in 0 until height) {
                        bitmap.setPixel(x, y, if (bitMatrix[x, y]) color else Color.TRANSPARENT)
                    }
                }
                ivQRCode.setImageBitmap(bitmap)
            } catch (e: WriterException) {
                e.printStackTrace()
            }

        } else {
            snackBar("Campos vazios")
        }
    }




}