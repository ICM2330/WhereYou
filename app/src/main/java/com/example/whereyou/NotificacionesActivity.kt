package com.example.whereyou

import DisplayChat
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.whereyou.databinding.ActivityNotificacionesBinding
import com.example.whereyou.datos.Contacto


private lateinit var binding : ActivityNotificacionesBinding

class NotificacionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contacts = listOf(
            Contacto(0,"Monserrate", "¡Has sido invitado a un nuevo grupo!", R.drawable.usuario_perfil_logo),
            Contacto(1,"Un nuevo amigo", "Daniel Florido se ha unido, ¡Salúdalo!", R.drawable.usuario_perfil_logo),
            Contacto(2,"¡Alerta!", "Alguien ha entrado a tu cuenta", R.drawable.usuario_perfil_logo),
            Contacto(3,"Un nuevo amigo", "Daniela Flores se ha unido, ¡Salúdalo!", R.drawable.usuario_perfil_logo)
            // Agrega más contactos aquí
        )
        val adapter = DisplayChat(this, contacts)
        binding.listaNotificaciones.adapter = adapter

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                 R.id.grupos -> {
                    // Acción cuando se selecciona el elemento "Grupos"

                     var intent = Intent(baseContext, GruposActivity::class.java)
                     startActivity(intent)

                    true
                }
                R.id.chats -> {
                    // Acción cuando se selecciona el elemento "Chats"
                    true
                }
                R.id.home -> {
                    // Acción cuando se selecciona el elemento "Home"
                    var intent = Intent(baseContext, HomeActivity::class.java)
                    startActivity(intent)

                    true
                }
                R.id.alertas -> {
                    // Acción cuando se selecciona el elemento "Alertas"
                    var intent = Intent(baseContext, NotificacionesActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.perfil -> {
                    // Acción cuando se selecciona el elemento "Perfil"
                    true
                }
                else -> false
            }
        }
    }
}