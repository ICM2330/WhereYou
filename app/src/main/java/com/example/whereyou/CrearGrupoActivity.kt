package com.example.whereyou

import ContactsAdapter
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.whereyou.databinding.ActivityCrearGrupoBinding
import androidx.activity.result.ActivityResultCallback
import androidx.core.content.ContextCompat
import com.example.whereyou.datos.Contactos

class CrearGrupoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrearGrupoBinding
    lateinit var adapter : ContactsAdapter
    var contactos= mutableListOf<Contactos>()
    //Permissions
    val getSimplePermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ActivityResultCallback {
            updateUI(it)
        })
    val projection = arrayOf(ContactsContract.Profile._ID, ContactsContract.Profile.DISPLAY_NAME_PRIMARY)
    

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
            /*
                    <ImageView
            android:id="@+id/imageView"
            android:layout_width="40dp"
            android:layout_height="match_parent"
            android:layout_marginLeft="25dp"
            android:layout_marginRight="4dp"
            app:srcCompat="@drawable/usuario_perfil_logo" />
             */
            updateUI(true)
            binding.listaContactos.setOnItemClickListener{parent, view, position, id ->
                val imageView = ImageView(this)
                imageView.setImageResource(R.drawable.usuario_perfil_logo) // Reemplaza 'tu_imagen' con el recurso de imagen que desees mostrar
                imageView.layoutParams = LinearLayout.LayoutParams(
                    (40 * density + 0.5f).toInt(),LinearLayout.LayoutParams.MATCH_PARENT
                )
                binding.linearL.addView(imageView)
            }
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                com.example.whereyou.R.id.grupos -> {
                    // Acción cuando se selecciona el elemento "Grupos"

                    var intent = Intent(baseContext, GruposActivity::class.java)
                    startActivity(intent)

                    true
                }
                com.example.whereyou.R.id.chats -> {
                    // Acción cuando se selecciona el elemento "Chats"
                    var intent = Intent(baseContext, ChatsActivity::class.java)
                    startActivity(intent)
                    true
                }
                com.example.whereyou.R.id.home -> {
                    // Acción cuando se selecciona el elemento "Home"
                    var intent = Intent(baseContext, HomeActivity::class.java)
                    startActivity(intent)

                    true
                }
                com.example.whereyou.R.id.alertas -> {
                    // Acción cuando se selecciona el elemento "Alertas"
                    var intent = Intent(baseContext, NotificacionesActivity::class.java)
                    startActivity(intent)
                    true
                }
                com.example.whereyou.R.id.perfil -> {
                    var intent = Intent(baseContext, PerfilActivity::class.java)
                    startActivity(intent)
                    // Acción cuando se selecciona el elemento "Perfil"
                    true
                }
                else -> false
            }
        }
    }

    fun updateUI(permission : Boolean){
        if(permission) {
            //granted
            val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, projection, null, null, null)
            adapter.changeCursor(cursor)
        }
        binding.GAMenuLogOut.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
    }
}