package com.example.whereyou

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.whereyou.databinding.ActivityCambiarFotoBinding
import com.example.whereyou.services.CloudStorageService
import com.parse.ParseUser
import java.io.ByteArrayOutputStream
import java.io.File


class CambiarFotoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCambiarFotoBinding
    private var cloudStorageService = CloudStorageService()
    val currentUser = ParseUser.getCurrentUser()
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

        val imageName =  "${currentUser.username.toString()}.jpg"
        Log.i("Imagen", "El nombre va a ser $imageName")

        cloudStorageService.uploadImageToFirebaseStorage(
            uri,
            imageName,
            { imageUrl ->
            Log.i("Imagen", "Se va a poner en el user con uri ${uri.toString()}")
            if (imageUrl != null) {
                currentUser.put("profilePic", imageUrl)
                Log.i("Imagen", "Se ha puesto en el user con uri $uri")
                finish()
            } else {
                // Manejar el caso de imageUrl nulo
                Log.e("Imagen", "La URL de la imagen es nula")
            }
        }, { exception ->
            Log.e("Imagen", "Error durante la carga de imagen: $exception")
            Toast.makeText(
                this,
                "No se ha podido guardar la foto de perfil",
                Toast.LENGTH_SHORT
            ).show()
        })
    }
}