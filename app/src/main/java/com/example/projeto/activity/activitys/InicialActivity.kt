package com.example.projeto.activity.activitys


import android.content.Intent

import android.net.Uri

import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projeto.R
import com.example.projeto.activity.model.NetworkUtils
import com.example.projeto.databinding.ActivityInicialBinding
import com.example.projeto.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class InicialActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityInicialBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkNetworkAndInitialize()
        botoes()
    }

    override fun onResume() {
        super.onResume()
        checkNetworkAndInitialize()
    }

    private fun checkNetworkAndInitialize() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            // Se a rede estiver disponível, execute suas ações aqui

        } else {
            showNoInternetSnackbar()
        }
    }

    private fun botoes(){

        binding.buttonCliente.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        binding.buttonPlanos.setOnClickListener {
            val intent = Intent(this, PlanosActivity::class.java)
            startActivity(intent)

        }

        binding.buttonNewCliente.setOnClickListener {
            val whatsapp =
                "https://api.whatsapp.com/send/?phone=5581986271986&text&type=phone_number&app_absent=0"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(whatsapp))
            startActivity(intent)

        }
    }

    private fun showNoInternetSnackbar() {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            R.string.conexao_internet,
            Snackbar.LENGTH_INDEFINITE
        ).setBackgroundTint(ContextCompat.getColor(this, R.color.rosa))

        snackbar.setAction("Abrir Configurações", View.OnClickListener {
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            startActivity(intent)
        })
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.amarelo))

        snackbar.show()
    }


}