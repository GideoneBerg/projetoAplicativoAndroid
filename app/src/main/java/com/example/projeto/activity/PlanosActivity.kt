package com.example.projeto.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto.R

class PlanosActivity : AppCompatActivity() {
    private var central: Button? = null
    private var whatsapp: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planos)
        central = findViewById<View>(R.id.button_central) as Button
        whatsapp = findViewById<View>(R.id.button_whatsapp) as Button
        whatsapp!!.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://api.whatsapp.com/send/?phone=5581986271986&text&type=phone_number&app_absent=0")
                )
            )
        }
        central!!.setOnClickListener {
            val number = "8134356078"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$number")
            startActivity(intent)
        }
    }
}