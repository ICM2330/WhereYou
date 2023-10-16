package com.example.whereyou

import AdaptadorContactos
import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import com.example.whereyou.databinding.ActivityCrearGrupoBinding
import com.example.whereyou.datos.Contactos

class CrearGrupoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrearGrupoBinding
    var contactos= mutableListOf<Contactos>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearGrupoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val contacts = listOf(
            Contactos("John Doe", "Qué bendición", R.drawable.ic_menu_camera),
            Contactos("Jane Smith", "Hola Mundo", R.drawable.ic_menu_camera),
            Contactos("Juan Carlos", "Usando WhereYou", R.drawable.ic_menu_camera),
            Contactos("Juan Manuel", "Rolling in the deep", R.drawable.ic_menu_camera)
            // Agrega más contactos aquí
        )

        val adapter = AdaptadorContactos(this, contacts)
        binding.listaContactos.adapter = adapter

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
    }
}