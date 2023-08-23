package com.example.whereyou

import AdaptadorContactos
import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import com.example.whereyou.databinding.ActivityCrearGrupoBinding
import com.example.whereyou.datos.Contactos

private lateinit var binding: ActivityCrearGrupoBinding
var contactos= mutableListOf<Contactos>()
class CrearGrupoActivity : AppCompatActivity() {
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
    }
}