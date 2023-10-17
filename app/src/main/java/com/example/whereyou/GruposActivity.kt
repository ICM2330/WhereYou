package com.example.whereyou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.whereyou.databinding.ActivityGruposBinding
import com.example.whereyou.HomeActivity
import com.google.firebase.auth.FirebaseAuth


class GruposActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityGruposBinding

    private val array = arrayOf("Familia", "Amigos", "Viaje a medellin", "Parche del viernes")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGruposBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var adapter = ArrayAdapter<String>(baseContext,android.R.layout.simple_list_item_1,array)
        binding.GAListaGrupos.adapter = adapter

        binding.GAListaGrupos.setOnItemClickListener { adapterView, view, i, l -> startActivity(Intent(baseContext,MapaGrupoActivity::class.java))}

        binding.GAMenuLogOut.setOnClickListener {
            auth.signOut()
            val i = Intent(this, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }

        binding.GANuevoContacto.setOnClickListener {
            startActivity(Intent(baseContext,CrearGrupoActivity::class.java))
        }

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