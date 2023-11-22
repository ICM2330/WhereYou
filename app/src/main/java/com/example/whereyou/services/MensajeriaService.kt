package com.example.whereyou.services

import com.example.whereyou.model.Mensaje
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MensajeriaService {
    val currentUser = ParseUser.getCurrentUser()

    fun obtenerHoraActual(): String {
        val formato = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val fecha = Date(System.currentTimeMillis())
        return formato.format(fecha)
    }

    // Guardar mensajes
    fun guardarMensaje(grupoId: String, contenidoDelMensaje: String){
        val nuevoMensaje = ParseObject("Mensaje")
        nuevoMensaje.put("grupoId", grupoId)
        nuevoMensaje.put("date", obtenerHoraActual())
        nuevoMensaje.put("user", currentUser)
        nuevoMensaje.put("content", contenidoDelMensaje)

        nuevoMensaje.saveInBackground { e ->
            if (e == null) {
                // El mensaje se guardó exitosamente
                // Notificaciones
            } else {
                // Manejar errores
            }
        }
    }


    // Obtener mensajes
    fun obtenerMensajes(grupoActual: String, grupoId: String, completion: (List<Mensaje>) -> Unit) {
        val query = ParseQuery.getQuery<ParseObject>("Mensaje")
        query.whereEqualTo("groupId", grupoId)
        query.findInBackground { messages, e ->
            if (e == null) {
                // messages contiene los mensajes del grupo
                val listaMensajes = mutableListOf<Mensaje>()

                for (message in messages) {
                    val hora = message.getString("hora")
                    val usuario = message.getString("usuario")
                    val contenido = message.getString("contenido")

                    println("Hora: $hora, Usuario: $usuario, Contenido: $contenido")

                    // Crear un objeto Mensaje y agregarlo a la lista
                    val mensaje = Mensaje(hora.toString(), usuario.toString(), contenido.toString())
                    listaMensajes.add(mensaje)
                }

                // Ordenar la lista por fecha (asumiendo que "hora" es una cadena en formato de fecha)
                val mensajesOrdenados = listaMensajes.sortedBy { it.hora }

                // Llamar a la función de finalización con la lista ordenada
                completion(mensajesOrdenados)
            } else {
                // Manejar errores
            }
        }
    }


    //cuando quiera obtener los mensajes:
    /*obtenerMensajes("nombreGrupo", "idGrupo") { mensajes ->
    // La variable 'mensajes' ahora contiene la lista de mensajes ordenados
    for (mensaje in mensajes) {
        // Procesar cada mensaje según sea necesario
    }
}*/
}