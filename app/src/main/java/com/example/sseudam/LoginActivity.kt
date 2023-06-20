package com.example.Sseudam

import User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.Sseudam.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    lateinit var db: DatabaseHelper



    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // ArrayList 객체를 가져옴


        db = DatabaseHelper(this)

        binding.btnLogin.setOnClickListener {
            createUser().let {
                if (it != null) {
                    if (db.login(it)) {
                        Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("name", binding.editId.text.toString())
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "로그인 실패!", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "정보를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
    fun createUser(): User?{
        val id = binding.editId.text.toString()
        val pw = binding.editPw.text.toString()
        val name = ""
        val phone = ""
        val point = ""
        if(id == "" || pw == "" || name != "" || phone != "" || point != "") // 입력 정보가 하나라도 비어있으면
            return null // Null 반환

        return User(id,pw, name, phone, point )
    }
}

















    /*
    lateinit var db: DatabaseHelper
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = DatabaseHelper(this)

    binding.btnRegister.setOnClickListener {
        createUser().let{
            if(it != null){
                db.addUser(it)
            }
        }
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
    binding.btnLogin.setOnClickListener {
        createUser().let{
            if(it != null){
                if(db.logIn(it)){
                    Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(this, "로그인 실패!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "정보를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
    }
    private fun createUser(): User?{
        val id = binding.editId.text.toString()
        val pw = binding.editPw.text.toString()
        val name = ""
        val phone = ""
        if(id == "" || pw == "")
            return null
        return User(id,pw, name, phone)
    }
}




    /*
    val DATABASE_VERSION = 1
    val DATABASE_NAME = "LocalDB.db"
    private lateinit var binding: ActivityLoginBinding
    private lateinit var localDB: LocalDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)    // 뷰바인딩 사용
        val view = binding.root
        setContentView(view)

        localDB= LocalDB(this, DATABASE_NAME,null, DATABASE_VERSION) // SQLite 모듈 생성

        binding.successLogin.setOnClickListener { view->
            val id = binding.editId.text.toString()
            val passwd = binding.editPw.text.toString()
            val exist = localDB.logIn(id,passwd) // 로그인 실행


            if(binding.editId.text.isEmpty()||binding.editPw.text.isEmpty()) {// 값이 전부 입력되지 않은경우
                Toast.makeText(this, "값을 전부 입력해주세요..", Toast.LENGTH_LONG).show()
            }
            if(exist){ // 로그인 성공
                val intent =Intent(this, MainActivity::class.java)
                startActivity(intent)
                Log.d("로그인 성공", "아이디 : $id 비밀번호 : $passwd ")
            }else{ // 실패
                Toast.makeText(this@LoginActivity, "아이디 혹은 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnRegister.setOnClickListener { view->
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.passLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onDestroy() {// 엑티비티가 종료시 close
        localDB.close()
        super.onDestroy()

         binding.successLogin.setOnClickListener {
            val id = binding.editId.text.toString()
            val passwd = binding.editPw.text.toString()
             // 로그인 실행


            if (binding.editId.text.isEmpty() || binding.editPw.text.isEmpty()) {// 값이 전부 입력되지 않은경우
                Toast.makeText(this, "값을 전부 입력해주세요..", Toast.LENGTH_LONG).show()
            }
            if (exist) { // 로그인 성공
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Log.d("로그인 성공", "아이디 : $id 비밀번호 : $passwd ")
            } else { // 실패
                Toast.makeText(this@LoginActivity, "아이디 혹은 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnRegister.setOnClickListener { view ->
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.passLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onDestroy() {
        databaseHelper.close()
        super.onDestroy()
    }
    }

     */


     */