package com.example.whereyou

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whereyou.databinding.ActivityLoginBinding
import com.example.whereyou.services.NotificationService
import com.parse.LogInCallback
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser


class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.login.setOnClickListener {
            val user = binding.username.text.toString()
            val pass = binding.password.text.toString()
            if (validateForm()){
                validateUser(user, pass)
            }
        }

        binding.register.setOnClickListener{
            startActivity(Intent(baseContext, RegisterActivity::class.java))
        }
    }

    
    fun validateUser(user: String, pass: String){
        ParseUser.logInInBackground(user, pass, LogInCallback { user, e ->
            if(user!=null) {
                startActivity(Intent(baseContext, HomeActivity::class.java))
                saveSession(user)
                finish()
            }else{
                Log.e("Usuario inexistente", e.message!!)
                binding.info.text = "Usuario o contraseña incorrectos"
            }
        })
    }
    private fun saveSession(user: ParseUser){

        //Guarda sesión local
        val sharedPreferences = getSharedPreferences("mi_app_pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userId", user.objectId)
        editor.putString("username", user.username)
        editor.apply()
    }
    private fun validateForm(): Boolean {
        if(binding.username.text.toString()==""|| binding.password.text.toString()==""){
            Toast.makeText(baseContext, "Complete los campos necesarios", Toast.LENGTH_LONG).show()
        }else{
            return true
        }
        return false
    }
}