package com.example.whereyou

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.whereyou.databinding.ActivityPerfilBinding


class PerfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imgPerfil.setOnClickListener(){
            var intent = Intent(baseContext, CambiarFotoActivity::class.java)
            startActivity(intent)

            //val imagenBitmap = intent.getParcelableExtra<Bitmap>("imagen_seleccionada")
            //if (imagenBitmap != null) {
                //Log.i("Perfila", "Entre aqui $imagenBitmap")
                //binding.imgPerfil.setImageBitmap(imagenBitmap)
            //}
        }
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