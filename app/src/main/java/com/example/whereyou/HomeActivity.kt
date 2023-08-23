package com.example.whereyou

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.whereyou.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.groups.setOnClickListener {
            startActivity(Intent(baseContext, GroupChatActivity::class.java))
        }

        binding.btnhome.setOnClickListener{
            startActivity(Intent(baseContext, HomeActivity::class.java))
        }
    }
}