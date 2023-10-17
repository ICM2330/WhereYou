package com.example.whereyou

import DisplayChat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whereyou.databinding.ActivityChatsBinding
import com.example.whereyou.datos.Contacto

class ChatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contacts = listOf(
            Contacto(0, "Familia", "Integrantes: Alicia Bareto, Jose Mora, Daniel Mora..", R.drawable.usuario_perfil_logo),
            Contacto(1,"Sofia Martinez", "Tu: Estuvo Increible", R.drawable.usuario_perfil_logo),
            Contacto(2,"Alicia Bareto", "Alicia Bareto: Donde estas?", R.drawable.usuario_perfil_logo),
            Contacto(3,"Viaje Santa Marta", "Integrantes: Sofia Martinez, Valentina Ruiz..", R.drawable.usuario_perfil_logo)
        )
        val adapter = DisplayChat(this, contacts)
        binding.listaChats.adapter = adapter
    }
}