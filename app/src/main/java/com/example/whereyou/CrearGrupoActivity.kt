package com.example.whereyou

import ContactsAdapter
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.whereyou.databinding.ActivityCrearGrupoBinding
import androidx.activity.result.ActivityResultCallback
import androidx.core.content.ContextCompat
import com.example.whereyou.datos.Contacto

class CrearGrupoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrearGrupoBinding
    lateinit var adapter : ContactsAdapter
    var contactos= mutableListOf<Contacto>()
    //Permissions
    val getSimplePermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ActivityResultCallback {
            updateUI(it)
        })
    val projection = arrayOf(ContactsContract.Profile._ID, ContactsContract.Profile.DISPLAY_NAME_PRIMARY)
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
        binding = ActivityCrearGrupoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ContactsAdapter(this, null, 0)
        binding.listaContactos.adapter = adapter

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED){
            if(shouldShowRequestPermissionRationale(android.Manifest.permission.READ_CONTACTS)){
                Toast.makeText(this, "The permission is required to show the contacts", Toast.LENGTH_LONG).show()
            }
            getSimplePermission.launch(android.Manifest.permission.READ_CONTACTS)
        }else {
            val density = resources.displayMetrics.density

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

            updateUI(true)
            binding.listaContactos.setOnItemClickListener{parent, view, position, id ->
                val imageView = ImageView(this)
                imageView.setImageResource(R.drawable.usuario_perfil_logo) // Reemplaza 'tu_imagen' con el recurso de imagen que desees mostrar
                imageView.layoutParams = LinearLayout.LayoutParams(
                    (40 * density + 0.5f).toInt(),LinearLayout.LayoutParams.MATCH_PARENT
                )
                binding.listaContactos.addView(imageView)
            }
        }
    }

    fun updateUI(permission : Boolean){
        if(permission) {
            //granted
            val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, projection, null, null, null)
            adapter.changeCursor(cursor)
        }
    }
}