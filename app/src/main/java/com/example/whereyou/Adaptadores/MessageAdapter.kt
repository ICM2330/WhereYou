package com.example.whereyou.Adaptadores

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CursorAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.whereyou.R
import com.example.whereyou.datos.Message

class MessageAdapter(context: Context, private val messages: List<Message>) :
        ArrayAdapter<Message>(context, 0, messages) {
        companion object {
                private const val TYPE_SENT = 0
                private const val TYPE_RECEIVED = 1
        }

        override fun getItemViewType(position: Int): Int {
                return if(messages[position].mandadoPorElUsuario){
                        TYPE_SENT
                }else{
                        TYPE_RECEIVED
                }
        }

        override fun getViewTypeCount(): Int {
                return 2
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val message = getItem(position)
                val viewType = getItemViewType(position)

                val inflater = LayoutInflater.from(context)
                val view: View = when (viewType) {
                        TYPE_SENT -> {
                                val viewSent = inflater.inflate(R.layout.list_right_chat_layout, parent, false)
                                val textViewMessageSent: TextView = viewSent.findViewById(R.id.chatText_R)
                                val userViewMessageSent: TextView = viewSent.findViewById(R.id.chatUser_R)
                                textViewMessageSent.text = message?.contenido
                                userViewMessageSent.text = message?.usuario
                                viewSent
                        }
                        TYPE_RECEIVED -> {
                                val viewReceived = inflater.inflate(R.layout.list_left_chat_layout, parent, false)
                                val textViewMessageReceived: TextView = viewReceived.findViewById(R.id.chatText_L)
                                val userViewMessageReceived: TextView = viewReceived.findViewById(R.id.chatUser_L)
                                textViewMessageReceived.text = message?.contenido
                                userViewMessageReceived.text = message?.usuario
                                viewReceived
                        }
                        else -> throw IllegalArgumentException("Invalid view type")
                }

                return view
        }
}


