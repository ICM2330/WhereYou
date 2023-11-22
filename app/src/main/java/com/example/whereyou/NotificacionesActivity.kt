package com.example.whereyou

import DisplayChat
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.whereyou.databinding.ActivityNotificacionesBinding
import com.example.whereyou.datos.Contacto
import com.google.android.material.bottomnavigation.BottomNavigationView

class NotificacionesActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNotificacionesBinding
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val clicked = item.itemId
        if(clicked == R.id.menuLogOut){

            val i = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }
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