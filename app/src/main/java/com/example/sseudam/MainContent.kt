package com.example.Sseudam

import User
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.Sseudam.UserListAdapter
import com.example.Sseudam.databinding.ActivityMainContentBinding


class MainContent : AppCompatActivity() {
    private lateinit var binding: ActivityMainContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val name = intent.getStringExtra("name").toString()
        binding.nameid.text = "안녕하세요 ${name}님"
    }
}