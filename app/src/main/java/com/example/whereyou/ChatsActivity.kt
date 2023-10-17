package com.example.whereyou

import ContactsAdapter
import DisplayChat
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.whereyou.databinding.ActivityChatsBinding
import com.example.whereyou.datos.Contacto
import com.google.firebase.auth.FirebaseAuth

class ChatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatsBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contacts = listOf(
            Contacto(0, "Familia", "Integrantes: Alicia Bareto, Jose Mora, Daniel Mora..", R.drawable.usuario_perfil_logo),
            Contacto(1, "Sofia Martinez", "Tu: Estuvo Increible", R.drawable.usuario_perfil_logo),
            Contacto(2, "Alicia Bareto", "Alicia Bareto: Donde estas?", R.drawable.usuario_perfil_logo),
            Contacto(3, "Viaje Santa Marta", "Integrantes: Sofia Martinez, Valentina Ruiz..", R.drawable.usuario_perfil_logo)
        )
        val adapter = DisplayChat(this, contacts)
        binding.listaChats.adapter = adapter

        binding.listaChats.setOnItemClickListener { adapterView, view, i, l ->
            startActivity(Intent(baseContext, GroupChatActivity::class.java))
        }

        binding.HAGrupos.setOnClickListener {
            var intent = Intent(baseContext, GruposActivity::class.java)
            startActivity(intent)
        }

        binding.HAChats.setOnClickListener {
            val  intent = Intent(baseContext,ChatsActivity::class.java)
            startActivity(intent)
        }

        binding.HAHome.setOnClickListener {
            var intent = Intent(baseContext, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.HAAlertas.setOnClickListener {
            var intent = Intent(baseContext, NotificacionesActivity::class.java)
            startActivity(intent)
        }

        binding.HAPerfil.setOnClickListener {
            startActivity(Intent(baseContext,PerfilActivity::class.java))
        }
        binding.GAMenuLogOut.setOnClickListener {
            auth.signOut()
            val i = Intent(this, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }

    }
}