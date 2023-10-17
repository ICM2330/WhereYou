package com.example.whereyou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whereyou.databinding.ActivityGroupChatBinding

class GroupChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGroupChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupChatBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.HAGrupos.setOnClickListener {
            var intent = Intent(baseContext, GruposActivity::class.java)
            startActivity(intent)
        }

        binding.HAChats.setOnClickListener {
            startActivity(Intent(baseContext,ChatsActivity::class.java))
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