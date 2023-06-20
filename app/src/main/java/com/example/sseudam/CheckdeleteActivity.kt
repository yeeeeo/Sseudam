package com.example.Sseudam

import User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.Sseudam.DatabaseHelper
import com.example.Sseudam.LoginActivity
import com.example.Sseudam.databinding.ActivityCheckdeleteBinding

class CheckdeleteActivity : AppCompatActivity() {
    lateinit var db: DatabaseHelper
    private lateinit var binding: ActivityCheckdeleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCheckdeleteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = DatabaseHelper(this)


        binding.delete.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            createUser().let {
                if (it != null) {
                    db.deleteUser(it)
                }
            }
        }
    }


    fun createUser(): User?{
        val id = binding.editId.text.toString()
        val pw = binding.editPw.text.toString()
        val name = ""
        val phone = ""
        val point = ""
        if(id == "" || pw == "" || name != "" || phone != "" || point !="") // 입력 정보가 하나라도 비어있으면
            return null // Null 반환

        return User(id,pw, name, phone, point)
    }
}