package com.example.projeto.activity.activitys


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projeto.R
import com.example.projeto.activity.classes.Lancamento
import com.example.projeto.activity.classes.Pix
import com.example.projeto.databinding.ActivityEscolhaPagamentoBinding
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EscolhaPagamento : AppCompatActivity() {

    private val binding  by lazy {
        ActivityEscolhaPagamentoBinding.inflate(layoutInflater)
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val lancamento = intent.getParcelableExtra("key", Lancamento::class.java)
     //   val lancamentoPix = intent.getParcelableExtra("pix", Pix::class.java)
        if(lancamento != null){
            binding.idFatura.text = lancamento.titulo
            binding.statusFatura.text = lancamento.status
            binding.valor.text = lancamento.valor
            binding.vencimento.text = lancamento.datavenc
            binding.codigo.text = lancamento.linhadig

        }

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

       binding.buttonGerarPix.setOnClickListener {
           gerarQRCode()
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
        var lancamentoPix = intent.getParcelableExtra("pix", Pix::class.java)

      //  lancamentoPix.qrcode


        //val textQRcode = binding.textqrCode.text
        val ivQRCode = binding.ivqrCode

        if (lancamentoPix != null){
            val texto: String = lancamentoPix.qrcode.toString()
            val multiFormatWriter = MultiFormatWriter()
            try {
                val bitMatrix = multiFormatWriter.encode(texto, BarcodeFormat.QR_CODE, 600, 600)
                val width = bitMatrix.width
                val height = bitMatrix.height
                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                for (x in 0 until width) {
                    for (y in 0 until height) {
                        bitmap.setPixel(x, y, if (bitMatrix[x, y]) -0x1000000 else -0x1)
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