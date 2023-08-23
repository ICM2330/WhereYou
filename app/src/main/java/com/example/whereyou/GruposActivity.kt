package com.example.whereyou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.whereyou.databinding.ActivityGruposBinding
import com.example.whereyou.HomeActivity

private lateinit var binding: ActivityGruposBinding
class GruposActivity : AppCompatActivity() {

    private val array = arrayOf("Familia", "Amigos", "Viaje a medellin", "Parche del viernes")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGruposBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var adapter = ArrayAdapter<String>(baseContext,android.R.layout.simple_list_item_1,array)
        binding.GAListaGrupos.adapter = adapter

        binding.GAListaGrupos.setOnItemClickListener { adapterView, view, i, l -> startActivity(Intent(baseContext,GroupChatActivity::class.java))}

        binding.GAOpciones.setOnClickListener {

        }

        binding.GAInformacion.setOnClickListener {

        }

        binding.GANuevoContacto.setOnClickListener {
			startActivity(Intent(baseContext,CrearGrupoActivity::class.java))
        }

        binding.GAGrupos.setOnClickListener {
            startActivity(Intent(baseContext,GruposActivity::class.java))
        }

        binding.GAChats.setOnClickListener {
            //startActivity(Intent(baseContext,ChatsActivity::class.java))
        }

        binding.GAHome.setOnClickListener {
            startActivity(Intent(baseContext, HomeActivity::class.java))
        }

        binding.GAAlertas.setOnClickListener {
            startActivity(Intent(baseContext,NotificacionesActivity::class.java))
        }

        binding.GAPerfil.setOnClickListener {
            //startActivity(Intent(baseContext,PerfilActivity::class.java))
        }
    }
}