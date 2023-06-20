package com.example.Sseudam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.Sseudam.databinding.ActivityMainBinding
import com.example.Sseudam.databinding.ActivityMantomanBinding

class MantomanActivity : AppCompatActivity() {
    lateinit var db: DatabaseHelper
    private lateinit var binding: ActivityMantomanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMantomanBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = intent
        var title = intent.getStringExtra("title")
        var contents = intent.getStringExtra("contents")

        intent.putExtra("title", title)
        intent.putExtra("contents", contents)

        binding.Title.text = "${title}"
        binding.Contents.text = "${contents}"
    }
}