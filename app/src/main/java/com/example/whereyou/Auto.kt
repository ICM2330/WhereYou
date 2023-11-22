package com.example.whereyou

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.whereyou.services.NotificationService

//import com.example.taller3movil.services.NotificationService


class Auto : BroadcastReceiver(){
    override fun onReceive(context: Context, arg: Intent) {
        /*if (arg.action === Intent.ACTION_BOOT_COMPLETED) {
            val intent = Intent(context, NotificationService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
            Log.i("Nico", "started")
        }
        Log.i("Nico", "Not started")*/
    }
}