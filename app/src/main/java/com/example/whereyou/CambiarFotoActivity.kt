package com.example.whereyou

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.whereyou.databinding.ActivityCambiarFotoBinding
import java.io.ByteArrayOutputStream
import java.io.File


class CambiarFotoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCambiarFotoBinding

    val getContentGallery = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            loadImage(it!!)
        }
    )

    val getContentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture(),
        ActivityResultCallback {
            if(it){
                loadImage(cameraUri)
            }
        })

    lateinit var cameraUri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCambiarFotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener {
            getContentGallery.launch("image/*")
        }

        binding.cameraButton.setOnClickListener {
            val file = File(getFilesDir(), "picFromCamera");
            cameraUri = FileProvider.getUriForFile(baseContext,baseContext.packageName + ".fileprovider", file)
            getContentCamera.launch(cameraUri)
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

    fun loadImage(uri : Uri){
        val imageStream = getContentResolver().openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(imageStream)
        binding.image.setImageBitmap(bitmap)
        //val intent = Intent(this, PerfilActivity::class.java)
        //intent.putExtra("image", bitmap)
        //startActivity(intent)
    }
}