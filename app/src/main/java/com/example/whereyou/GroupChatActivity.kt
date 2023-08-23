package com.example.whereyou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whereyou.databinding.ActivityGroupChatBinding

class GroupChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGroupChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}