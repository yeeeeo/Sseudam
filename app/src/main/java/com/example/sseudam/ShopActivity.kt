package com.example.Sseudam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.example.Sseudam.databinding.ActivityShopBinding

class ShopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopBinding
    var point:Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityShopBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val intent = intent
        var name = intent.getStringExtra("name")
        var point = intent.getStringExtra("point")
        binding.mainBottombar.setOnItemSelectedListener {

            when(it.itemId){
                R.id.bottom_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("name", name)
                    intent.putExtra("point",point)
                    startActivity(intent)
                }
                R.id.bottom_shop -> {
                    val intent = Intent(this, ShopActivity::class.java)
                    intent.putExtra("name", name)
                    intent.putExtra("point",point)
                    startActivity(intent)
                }
                R.id.bottom_my -> {
                    val intent = Intent(this, MypageActivity::class.java)
                    intent.putExtra("name", name)
                    intent.putExtra("point",point)
                    startActivity(intent)
                }

            }

            true

        }
        val mainBottombar: Menu = binding.mainBottombar.getMenu()
        mainBottombar.setGroupCheckable(0, true, false)
        mainBottombar.findItem(R.id.bottom_home).setChecked(false)
        mainBottombar.findItem(R.id.bottom_shop).setChecked(true)
        mainBottombar.findItem(R.id.bottom_my).setChecked(false)
    }
}