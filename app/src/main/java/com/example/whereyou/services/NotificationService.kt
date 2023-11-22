package com.example.whereyou.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.whereyou.GroupChatActivity
import com.example.whereyou.R
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.livequery.ParseLiveQueryClient
import com.parse.livequery.SubscriptionHandling

class NotificationService : Service() {
    var notid = 0
    override fun onCreate(){
        super.onCreate()
        Log.i("Nicolas", "se esta escuchando notificacion")
        createNotificationChannel()
    }
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel"
            val description = "channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Test", name, importance)
            channel.setDescription(description)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel (channel)
        }
    }

    fun buildNotification(title: String, message: String, icon: Int, target: Class<*>, userMessage: ParseObject) : Notification {
        val builder =  NotificationCompat.Builder(this, "Test")
        builder.setSmallIcon(icon)
        builder.setContentTitle(title)
        builder.setContentText(message)
        val intent = Intent(this, target)
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)
        builder.setAutoCancel(true) //Remueve la notificación cuando se toque
        return builder.build()
    }

    fun notify(notification: Notification) {
        notid++

        val notificationManager = NotificationManagerCompat.from(this)

        if(checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(notid, notification)
        }
        if(notificationManager.areNotificationsEnabled()){
            notificationManager.notify(notid, notification)
        }else{
            Log.i("Nicolas", "No hay permisos")
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Toast.makeText(this, "MyService Completed or Stopped.", Toast.LENGTH_SHORT).show()
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        var parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient()

        Log.i("Nicolas", "Entre a start")


        val query = ParseQuery.getQuery<ParseObject>("Message")
        query.whereContains("createdAt", "2023")
        val subscriptionHandling = parseLiveQueryClient.subscribe(query)
        subscriptionHandling.handleEvents { query, event, obj ->
            if (event == SubscriptionHandling.Event.CREATE) {
                notify(buildNotification("Mensaje", "El usuario ${obj?.getString("owner")} ha enviado un mensaje",
                    R.drawable.baseline_notifications_24, GroupChatActivity::class.java, obj))
            }
        }
        return START_STICKY
    }
}