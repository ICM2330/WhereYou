package com.example.whereyou

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.whereyou.databinding.ActivityRegisterBinding
import com.parse.ParseException
import com.parse.ParseObject

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
            Log.i(ContentValues.TAG, "Attempt to write on parse");
            var firstObject = ParseObject.create("LoginUser")
            val username = binding.registerUsername.getText().toString();
            val password = binding.registerPassword.getText().toString();
            firstObject.put("username", username);
            firstObject.put("password", password);
            firstObject.saveInBackground {
                if (it != null) {
                    Log.e(ContentValues.TAG, it.getLocalizedMessage());
                } else {
                    Log.d(ContentValues.TAG, "Object saved.");
                    finish()
                }
            }
        }
    }

    fun validateForm(): Boolean {
        if(binding.registerUsername.text.toString()==""|| binding.registerPassword.text.toString()==""){
            Toast.makeText(baseContext, "Complete los campos necesarios", Toast.LENGTH_LONG).show()
        }else{
            return true
        }
        return false
    }

}