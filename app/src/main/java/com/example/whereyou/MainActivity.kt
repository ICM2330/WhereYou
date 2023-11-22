package com.example.whereyou

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.parse.ParseUser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("mi_app_pref", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)
        val username = sharedPreferences.getString("username", null)
        if (userId != null && username != null) {
            Log.i("Usuario activo: ", ParseUser.getCurrentUser().username)
            startActivity(Intent(baseContext, HomeActivity::class.java))
        } else {
            startActivity(Intent(baseContext, LoginActivity::class.java))
        }
        finish()
    }
}