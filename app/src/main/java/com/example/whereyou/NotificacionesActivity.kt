package com.example.whereyou

import AdaptadorContactos
import android.content.Intent
import com.example.whereyou.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.whereyou.databinding.ActivityNotificacionesBinding
import com.example.whereyou.datos.Contactos
import com.google.android.material.bottomnavigation.BottomNavigationView




class NotificacionesActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNotificacionesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contacts = listOf(
            Contactos("Monserrate", "¡Has sido invitado a un nuevo grupo!", R.drawable.usuario_perfil_logo),
            Contactos("Un nuevo amigo", "Daniel Florido se ha unido, ¡Salúdalo!", R.drawable.usuario_perfil_logo),
            Contactos("¡Alerta!", "Alguien ha entrado a tu cuenta", R.drawable.usuario_perfil_logo),
            Contactos("Un nuevo amigo", "Daniela Flores se ha unido, ¡Salúdalo!", R.drawable.usuario_perfil_logo)
            // Agrega más contactos aquí
        )
        val adapter = AdaptadorContactos(this, contacts)
        binding.listaNotificaciones.adapter = adapter


        binding.GAGrupos.setOnClickListener {
            startActivity(Intent(baseContext,GruposActivity::class.java))
        }

        binding.GAChats.setOnClickListener {
            startActivity(Intent(baseContext,ChatsActivity::class.java))
        }

        binding.GAHome.setOnClickListener {
            startActivity(Intent(baseContext, HomeActivity::class.java))
        }

        binding.GAAlertas.setOnClickListener {
            startActivity(Intent(baseContext,NotificacionesActivity::class.java))
        }

        binding.GAPerfil.setOnClickListener {
            startActivity(Intent(baseContext,PerfilActivity::class.java))
        }
    }
}