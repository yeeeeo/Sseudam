package com.example.Sseudam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.Sseudam.databinding.ActivityMypageInquiryBinding


class Mypage_InquiryActivity : AppCompatActivity() {
    lateinit var db: DatabaseHelper
    private lateinit var binding: ActivityMypageInquiryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMypageInquiryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var name = intent.getStringExtra("name")
        var title = intent.getStringExtra("title")
        var contents = intent.getStringExtra("contents")



        binding.goBack.setOnClickListener {
            val intent =Intent(this, MypageActivity::class.java)
            intent.putExtra("name", name)
            startActivity(intent)
        }

        binding.checkSuccess.setOnClickListener {
                        Toast.makeText(this, "접수완료", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MypageActivity::class.java)
                        intent.putExtra("name", name)
                        intent.putExtra("title", title)
                        intent.putExtra("contents", contents)
                        startActivity(intent)
                }




        }

    }
    /*
    fun createmantoman(): Comunity?{
        val title = binding.title.text.toString()
        val contents = binding.contents.text.toString()
        if(title == "" || contents == "") // 입력 정보가 하나라도 비어있으면
            return null // Null 반환

        return Comunity(title, contents )
    }

     */

