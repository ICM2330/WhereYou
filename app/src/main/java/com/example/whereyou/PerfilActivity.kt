package com.example.whereyou

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.whereyou.databinding.ActivityPerfilBinding
import com.google.firebase.auth.FirebaseAuth

class PerfilActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityPerfilBinding
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val clicked = item.itemId
        if(clicked == R.id.menuLogOut){
            auth.signOut()
            val i = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonCambiarFoto.setOnClickListener{
            var intent = Intent(baseContext, CambiarFotoActivity::class.java)
            startActivity(intent)
        }

        binding.HAChats.setOnClickListener{
            startActivity(Intent(baseContext, ChatsActivity::class.java))
        }

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

        binding.GAMenuLogOut.setOnClickListener {
            auth.signOut()
            val i = Intent(this, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
    }



}