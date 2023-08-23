package com.example.whereyou

import AdaptadorContactos
import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whereyou.databinding.ActivityNotificacionesBinding
import com.example.whereyou.datos.Contactos

private lateinit var binding : ActivityNotificacionesBinding

class NotificacionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contacts = listOf(
            Contactos("Monserrate", "¡Has sido invitado a un nuevo grupo!", R.drawable.ic_dialog_alert),
            Contactos("Un nuevo amigo", "Daniel Florido se ha unido, ¡Salúdalo!", R.drawable.ic_dialog_alert),
            Contactos("¡Alerta!", "Alguien ha entrado a tu cuenta", R.drawable.ic_dialog_alert),
            Contactos("Un nuevo amigo", "Daniela Flores se ha unido, ¡Salúdalo!", R.drawable.ic_dialog_alert)
            // Agrega más contactos aquí
        )

        val adapter = AdaptadorContactos(this, contacts)
        binding.listaNotificaciones.adapter = adapter
    }
}