package com.example.Sseudam

import User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.Sseudam.UserListAdapter
import com.example.Sseudam.databinding.ActivitySelectBinding

class SelectActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySelectBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val intent = intent
        // ArrayList 객체를 가져옴
        val users = intent.getSerializableExtra("users") as ArrayList<User>

        val userListAdapter = UserListAdapter()
        userListAdapter.submitList(users)
        binding.recyclerView.adapter = userListAdapter

    }
}