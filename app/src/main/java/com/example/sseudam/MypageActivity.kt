package com.example.Sseudam

import User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.example.Sseudam.Mypage_InquiryActivity
import com.example.Sseudam.databinding.ActivityMypageBinding
import com.example.Sseudam.CheckdeleteActivity


class MypageActivity : AppCompatActivity() {
    lateinit var db:DatabaseHelper
    var users = ArrayList<User>()
    var cms = ArrayList<Comunity>()
    var point:Double = 0.0

    private lateinit var binding: ActivityMypageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMypageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var name = intent.getStringExtra("name")
        var title = intent.getStringExtra("title")
        var contents = intent.getStringExtra("contents")

        db = DatabaseHelper(this)

        binding.btndelete.setOnClickListener {
            val intent = Intent(this, CheckdeleteActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("point",binding.pointText.text.toString())
            startActivity(intent)
        }

        binding.manToman.setOnClickListener {
            val intent = Intent(this, Mypage_InquiryActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("point",binding.pointText.text.toString())
            startActivity(intent)
        }

        binding.savemantoman.setOnClickListener {
            val intent = Intent(this, MantomanActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("point",binding.pointText.text.toString())
            intent.putExtra("title", title)
            intent.putExtra("contents", contents)
            startActivity(intent)
        }



        val intent = intent
        var point = intent.getStringExtra("point")
        binding.myid.text = "${name}"
        binding.pointText.text = "${point}"
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
        mainBottombar.findItem(R.id.bottom_shop).setChecked(false)
        mainBottombar.findItem(R.id.bottom_my).setChecked(true)
    }

}