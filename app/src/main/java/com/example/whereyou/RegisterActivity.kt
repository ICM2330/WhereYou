package com.example.whereyou

import android.content.ContentValues
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.whereyou.databinding.ActivityRegisterBinding
import com.parse.ParseUser
import java.util.Base64

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
                    notificate()
                    Log.d(ContentValues.TAG, "Singed up successfully - Object saved.");
                    finish()
                }
            }
        }
    }

    private fun notificate() {
        val queue = Volley.newRequestQueue(baseContext)
        val url = "https://rest-ww.telesign.com/v1/messaging"
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener<String> { response ->
                Log.i("Respuesta exitosa", response)
            },
            Response.ErrorListener { error ->
                // Manejar errores aquí.
                Log.i("Hubo un error", error.message!!)
            }
        ){
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["is_primary"] = "true"
                params["phone_number"] = "57"+binding.registerContact.text.toString()
                params["message"] = "Hola "+binding.registerName.text.toString()+" desde WhereYou"
                params["message_type"] = "ARN"
                return params
            }

            // Configurar las cabeceras de la solicitud para indicar el formato `x-www-form-urlencoded`.
            @RequiresApi(Build.VERSION_CODES.O)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                val credentials = "EA435217-5D16-47F6-900A-1966BED4444A:sK5CUdPtG5qbJ4FO3E5e/SkWMaNjQTY6QJCBBdBEpNpLZJli31v2bDMxPrEsWyERy0gJ5+Cyws7XXCrlIFrqDg=="  //
                val auth = "Basic " + Base64.getEncoder().encodeToString(credentials.toByteArray())
                headers["Authorization"] = auth
                headers["Content-Type"] = "application/x-www-form-urlencoded"
                return headers
            }
        }
        queue.add(stringRequest)
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