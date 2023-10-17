package com.example.whereyou

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whereyou.databinding.ActivityLoginBinding
import com.parse.ParseObject
import com.parse.ParseQuery


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
        var correcto = true
        val query: ParseQuery<ParseObject> = ParseQuery.getQuery("LoginUser")
        Log.i("Query", query.toString())
        query.findInBackground { objects, _ ->
            if (objects != null) {
                for (row in objects) {
                    val name = row["username"] as String?
                    val password = row["password"] as String?
                    if(name.equals(user)&&password.equals(pass)){
                        startActivity(Intent(baseContext, HomeActivity::class.java))
                        correcto = true
                        break;
                    } else{
                        correcto = false
                    }
                }
                if(correcto == false){
                    binding.info.text = "Usuario o contraseña incorrectos"
                }
            }
        }
    }

    fun validateForm(): Boolean {
        if(binding.username.text.toString()==""|| binding.password.text.toString()==""){
            Toast.makeText(baseContext, "Complete los campos necesarios", Toast.LENGTH_LONG).show()
        }else{
            return true
        }
        return false
    }
}