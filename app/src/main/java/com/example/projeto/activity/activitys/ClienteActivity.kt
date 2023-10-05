package com.example.projeto.activity.activitys

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button

import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    @SuppressLint("SetTextI18n")
    private fun dadosAPI() {

        val extras = intent.extras ?: return

        // Trazendo dados para a activity
        val nomeUsuario = intent.getStringExtra("nomeUsuario")

        binding.textViewNome.text = nomeUsuario
        binding.plano.text = extras.getString("plano")
        binding.vencimento.text = extras.getString("vencimento")

        val cidade =  extras.getString("cidade")
        val rua =  extras.getString("rua")
        val nascimento =  extras.getString("nascimento")
        val numeroCasa =  extras.getString("numero")
        val bairroCasa =  extras.getString("bairro")
        val estado =  extras.getString("estado")

        // Botao criado para exibição de dados do cliente usando popup
        binding.maisDados.setOnClickListener{
            val builder = AlertDialog.Builder(this)
//             builder.setTitle("Dados do Cliente")

            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.popup, null)
            builder.setView(dialogLayout)

            val dialog = builder.create()
            // Personaliza o estilo do popup
            dialog.window?.setBackgroundDrawableResource(R.drawable.backgroud_popup)

            // dados do cliente no popup
            val nome = dialogLayout.findViewById<TextView>(R.id.nome)
            nome.text = "Nome: $nomeUsuario"

            val ruaCliente = dialogLayout.findViewById<TextView>(R.id.endereco)
            ruaCliente.text = "End: $rua, n° $numeroCasa"

            val cidadeAtual = dialogLayout.findViewById<TextView>(R.id.cidade)
            cidadeAtual.text = "Cidade: $cidade, $estado"

            val nasc = dialogLayout.findViewById<TextView>(R.id.nascimento)
            nasc.text = "Nascimento: $nascimento"

            val bairro = dialogLayout.findViewById<TextView>(R.id.bairro)
            bairro.text = "Bairro: $bairroCasa"

            // Fechar o popup quando o botão "OK" é clicado

            val buttonOk = dialogLayout.findViewById<Button>(R.id.button_ok)
            buttonOk.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

    }
    private fun botoesScroll(){

        binding.btnLogout.setOnClickListener {
            realizarLogout()
        }

        binding.site.setOnClickListener{
            openUrl("https://arteempc.com.br:6565/")
        }

        binding.whatsapp.setOnClickListener {
            openUrl("https://api.whatsapp.com/send/?phone=5581986271986&text&type=phone_number&app_absent=0")
        }

        binding.facebook.setOnClickListener {
            openUrl("https://www.facebook.com/ARTEEMPC?mibextid=ZbWKwL")
        }

        binding.instagram.setOnClickListener{
            openUrl("https://www.instagram.com/arteempc/")
        }
        // Scroll
        binding.centralCliente.setOnClickListener{
            val telefone = "8134356078"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data =Uri.parse("tel:$telefone")
            startActivity(intent)
        }

        binding.testeVelocidade.setOnClickListener {
            val intent = Intent(this, WebSpeedTestActivity::class.java)
            startActivity(intent)
        }

        binding.sobre.setOnClickListener{
            val intent = Intent(this, SobreActivity::class.java)
            startActivity(intent)
        }

        binding.segundaVia.setOnClickListener{
            val builder = AlertDialog.Builder(this)
//             builder.setTitle("Dados do Cliente")

            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.popup_segunda_via, null)
            builder.setView(dialogLayout)

            val dialog = builder.create()
            // Personaliza o estilo do popup
            dialog.window?.setBackgroundDrawableResource(R.drawable.background_popup_segundavia)

            // Fechar o popup quando o botão "OK" é clicado

            val buttonOk = dialogLayout.findViewById<Button>(R.id.button_ok)
            buttonOk.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        binding.pagamentoPix.setOnClickListener{
            val builder = AlertDialog.Builder(this)
//             builder.setTitle("Dados do Cliente")

            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.popup_segunda_via, null)
            builder.setView(dialogLayout)

            val dialog = builder.create()
            // Personaliza o estilo do popup
            dialog.window?.setBackgroundDrawableResource(R.drawable.background_popup_segundavia)

            // Fechar o popup quando o botão "OK" é clicado

            val buttonOk = dialogLayout.findViewById<Button>(R.id.button_ok)
            buttonOk.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun realizarLogout() {


// Lógica de logout personalizada aqui
        // Por exemplo, você pode limpar informações de sessão, preferências compartilhadas, etc.



// Limpar as preferências compartilhadas
        val sharedPreferences = getSharedPreferences("suas_preferencias", MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        editor.clear()
        editor.apply()

        editor.clear()
// Redirecionar o usuário para a tela de login
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()

        Toast.makeText(this, "Logout realizado com sucesso", Toast.LENGTH_SHORT).show()
    }


    private fun openUrl(url: String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }


}