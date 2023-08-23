package com.example.whereyou

import AdaptadorContactos
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whereyou.databinding.ActivityChatsBinding
import com.example.whereyou.datos.Contactos

class ChatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contacts = listOf(
            Contactos("Familia", "Integrantes: Alicia Bareto, Jose Mora, Daniel Mora..", R.drawable.usuario_perfil_logo),
            Contactos("Sofia Martinez", "Tu: Estuvo Increible", R.drawable.usuario_perfil_logo),
            Contactos("Alicia Bareto", "Alicia Bareto: Donde estas?", R.drawable.usuario_perfil_logo),
            Contactos("Viaje Santa Marta", "Integrantes: Sofia Martinez, Valentina Ruiz..", R.drawable.usuario_perfil_logo)
        )
        val adapter = AdaptadorContactos(this, contacts)
        binding.listaChats.adapter = adapter

    }
}