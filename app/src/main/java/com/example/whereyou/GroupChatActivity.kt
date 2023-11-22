package com.example.whereyou

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whereyou.Adaptadores.MessageAdapter
import com.example.whereyou.databinding.ActivityGroupChatBinding
import com.example.whereyou.datos.Message
import com.google.firebase.auth.FirebaseAuth
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.livequery.ParseLiveQueryClient
import com.parse.livequery.SubscriptionHandling

class GroupChatActivity : AppCompatActivity() {
    private val messageList = ArrayList<Message>()
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var listView: ListView
    private lateinit var binding : ActivityGroupChatBinding
    private lateinit var auth: FirebaseAuth

    private var prueba = false
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
        binding = ActivityGroupChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listView = binding.listGroup

        messageAdapter = MessageAdapter(this, messageList)
        listView.adapter = messageAdapter

        binding.enviarMensaje.setOnClickListener {
            if(prueba){ //Codigo de mensaje enviado
                val messageText = binding.GABuscador.text.toString().trim()
                val messageUser = "Jaime"
                sendMessage(messageText, messageUser, true)
                listView.smoothScrollToPosition(messageList.size - 1)
                binding.GABuscador.text.clear()
                prueba = false
            }else{ //Codigo de mensaje recibido
                val messageText = binding.GABuscador.text.toString().trim()
                val messageUser = "Pepe"
                sendMessage(messageText, messageUser, false)
                binding.GABuscador.text.clear()
                prueba = true
            }

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

        var parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient()

        val query = ParseQuery.getQuery<ParseObject>("Message")
        query.whereContains("createdAt", "2023")
        val subscriptionHandling = parseLiveQueryClient.subscribe(query)
        subscriptionHandling.handleEvents { query, event, obj ->
            if (event == SubscriptionHandling.Event.CREATE) {
                listView.adapter = messageAdapter
            }
        }
    }

    fun sendMessage(contenido: String, usuario: String, enviadoPorUsuario: Boolean){
        if(contenido.isNotEmpty() && contenido.isNotBlank() && usuario.isNotEmpty() && usuario.isNotBlank()){
            val message = Message(contenido, usuario,enviadoPorUsuario)
            messageList.add(message)
            messageAdapter.notifyDataSetChanged()
        }
    }

}

