package com.example.whereyou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.whereyou.databinding.ActivityLoginBinding
import com.example.whereyou.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val handler = Handler()
        handler.postDelayed({
            val binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }, 3000)
    }
}