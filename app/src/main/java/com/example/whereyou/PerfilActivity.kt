package com.example.whereyou

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.whereyou.databinding.ActivityPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.parse.ParseUser
import com.bumptech.glide.Glide
import com.example.whereyou.services.NotificationService


class PerfilActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPerfilBinding
    val currentUser = ParseUser.getCurrentUser()
    val profilePicUrl = currentUser.getString("profilePic")

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
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)



        actualizarPantalla()

        binding.cambiarFoto.setOnClickListener{
            var intent = Intent(baseContext, CambiarFotoActivity::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener{
            cerrarSesion()
            val i = Intent(this, LoginActivity::class.java)
            val serviceIntent = Intent(this, NotificationService::class.java)
            stopService(serviceIntent)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
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
    }

    private fun actualizarPantalla() {
        if(ParseUser.getCurrentUser()!=null){
            val user = ParseUser.getCurrentUser()
            val name =  ""+user.get("name")+" "+user.get("lastname")
            binding.fullname.text=name
            binding.profile.text="@"+user.username
            binding.correoElectronico.text=user.email
            binding.fechaRegistro.text = "Miembro desde: "+user.createdAt
            val profilePicUrl = currentUser.getString("profilePic")
            if (profilePicUrl != null && profilePicUrl.isNotBlank()) {
                Glide.with(this)
                    .load(profilePicUrl)
                    .into(binding.foto)
            }
        }
    }

    private fun cerrarSesion(){
        //Cierra sesión localmente
        val sharedPreferences = getSharedPreferences("mi_app_pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        ParseUser.logOut() //Cierra sesión en Parse
    }



}