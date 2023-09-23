package com.example.projeto.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projeto.activity.webView.WebSpeedTestActivity
import com.example.projeto.databinding.ActivitySobreBinding

class SobreActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySobreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySobreBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.sobreApc.setOnClickListener {
            val intent = Intent(this, WebSpeedTestActivity::class.java)
            startActivity(intent)
        }
        binding.idpoliticaPrivacidade.setOnClickListener {
            val intent = Intent(this, PoliticaPrivacidadeActivity::class.java)
            startActivity(intent)
        }
        binding.termosUso.setOnClickListener {
            val intent = Intent(this, WebSpeedTestActivity::class.java)
            startActivity(intent)
        }

    }


}