package com.example.whereyou

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.whereyou.databinding.ActivityRegisterBinding
import com.parse.ParseUser

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registeruser.setOnClickListener {
            saveData()
        }

    }

    fun saveData() {
        if(validateForm()) {
            var user = ParseUser()
            user.username = binding.registerUsername.text.toString()
            user.email = binding.registerEmail.text.toString()
            user.setPassword(binding.registerPassword.text.toString())
            user.put("name", binding.registerName.text.toString())
            user.put("lastname", binding.registerLastname.text.toString())
            user.put("contact", binding.registerContact.text.toString())
            user.signUpInBackground {
                if (it != null) {
                    Log.e(ContentValues.TAG, it.getLocalizedMessage());
                } else {
                    Log.d(ContentValues.TAG, "Singed up successfully - Object saved.");
                    finish()
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        if(binding.registerUsername.text.toString()=="" || binding.registerPassword.text.toString()=="" || binding.registerEmail.text.toString()=="" || binding.registerName.text.toString()=="" || binding.registerLastname.text.toString()=="" || binding.registerContact.text.toString()==""){
            Toast.makeText(baseContext, "Complete todos los campos", Toast.LENGTH_LONG).show()
        }else{
            return true
        }
        return false
    }

}