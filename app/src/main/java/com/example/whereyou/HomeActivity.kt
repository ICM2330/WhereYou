package com.example.whereyou

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.whereyou.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
		
		binding.HABorrar.setOnClickListener {
            binding.HABusquedaUbicacion.setText("")
        }

        binding.HAOpciones.setOnClickListener {

        }

        binding.HAMicrofono.setOnClickListener {

        }

        binding.HAUbicacion.setOnClickListener {
            
        }

        binding.HAGrupos.setOnClickListener {
            startActivity(Intent(baseContext,GruposActivity::class.java))
        }

        binding.HAChats.setOnClickListener {
            //startActivity(Intent(baseContext,ChatsActivity::class.java))
        }

        binding.HAHome.setOnClickListener {
            startActivity(Intent(baseContext,HomeActivity::class.java))
        }

        binding.HAAlertas.setOnClickListener {
            //startActivity(Intent(baseContext,AlertasActivity::class.java))
        }

        binding.HAPerfil.setOnClickListener {
            //startActivity(Intent(baseContext,PerfilActivity::class.java))
        }
    }
}