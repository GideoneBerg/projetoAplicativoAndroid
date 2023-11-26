package com.example.projeto.activity.activitys

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projeto.R
import com.example.projeto.databinding.ActivityInicialBinding
import com.google.android.material.snackbar.Snackbar

class InicialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInicialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicialBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (isNetworkAvailable()) {
            botoes()

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

    @SuppressLint("MissingPermission")
    private fun isNetworkAvailable(): Boolean {
        var result = false
        runOnUiThread {
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            result = capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        }
        return result
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