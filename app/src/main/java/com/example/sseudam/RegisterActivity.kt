package com.example.Sseudam

import User
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.example.Sseudam.databinding.ActivityLoginBinding
import com.example.Sseudam.databinding.ActivityRegisterBinding
import java.lang.Exception


class RegisterActivity : AppCompatActivity() {
    lateinit var db: DatabaseHelper
    var users = ArrayList<User>()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        binding.btnRegister.setOnClickListener {
            createUser().let {
                if (it != null) {
                    db.addUser(it)
                }
            }
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }



    }

    private fun createUser(): User? {
        val id = binding.etId.text.toString()
        val pw = binding.etPw.text.toString()
        val name = binding.etName.text.toString()
        val phone = binding.etPhone.text.toString()
        val point =""
        if (id == "" || pw == "" || name == "" || phone == "" || point != "") // 입력 정보가 하나라도 비어있으면
            return null // Null 반환

        return User(id, pw, name, phone, point)
    }
}


/*
private val binding: ActivityRegisterBinding by lazy{
    ActivityRegisterBinding.inflate(layoutInflater)
}
private val databaseHelper: DatabaseHelper by lazy{
    DatabaseHelper.getInstance(applicationContext)
}
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//
//        insertDb()
//
//    }  유튜브는 이거 없음

override fun onCreate(db: SQLiteDatabase?){
    var createQuery = "Create Table $TABLE_NAME(" + "$COL1_ID INTEGER PRIMARY KEY AUTOINCREA"
}

override fun onDestroy() {
    databaseHelper.close()
    super.onDestroy()
}
private fun clearEditTexts(){
    with(binding){
        etId.setText("")
        etPw.setText("")
        etName.setText("")
        etPhone.setText("")
    }
}

private fun insertDb() {
    binding.btnRegister.setOnClickListener {
        try {
            databaseHelper.insertData(
                binding.etId.text.toString().trim(),
                binding.etPw.text.toString().trim(),
                binding.etName.text.toString().trim(),
                binding.etPhone.text.toString().trim()
            )
            clearEditTexts()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if(binding.etId.text.isEmpty()||binding.etPw.text.isEmpty()||binding.etPwRe.text.isEmpty()||
            binding.etName.text.isEmpty()||binding.etPhone.text.isEmpty()){// 값이 전부 입력되지 않은경우
            Toast.makeText(this,"값을 전부 입력해주세요..", Toast.LENGTH_LONG).show()
        }else{
            if(binding.etPw.text.toString().equals(binding.etPwRe.text.toString())){//패스워드/패스워드 확인이 일치
                if(databaseHelper.checkIdExist(binding.etId.text.toString())){// 아이디 중복 확인
                    Toast.makeText(this,"아이디가 이미 존재합니다.", Toast.LENGTH_LONG).show()
                }else{// 존재하는 아이디
                    databaseHelper.insertData(binding.etId.text.toString(),binding.etPw.text.toString(),binding.etName.text.toString(),
                        binding.etPhone.text.toString())
                }
            }else{ // 패스워드/패스워드 확인이 일치하지 않음
                Toast.makeText(this,"패스워드가 틀렸습니다.", Toast.LENGTH_LONG).show()
            }
        }
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}
/*
private lateinit var binding: ActivityRegisterBinding
private lateinit var localDB: LocalDB
val DATABASE_VERSION = 1
val DATABASE_NAME = "LocalDB.db"


override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRegisterBinding.inflate(layoutInflater)    // 뷰 바인딩
    val view = binding.root
    setContentView(view)


    localDB= LocalDB(this, DATABASE_NAME,null, DATABASE_VERSION) // SQLite 모듈 생성

    binding.btnRegister.setOnClickListener { view->
        if(binding.regEditId.text.isEmpty()||binding.regEditPw.text.isEmpty()||binding.regEditPwRe.text.isEmpty()||
            binding.writeName.text.isEmpty()||binding.writeNumber.text.isEmpty()||binding.writeJoin.text.isEmpty()){// 값이 전부 입력되지 않은경우
            Toast.makeText(this,"값을 전부 입력해주세요..", Toast.LENGTH_LONG).show()
        }else{
            if(binding.regEditPw.text.toString().equals(binding.regEditPwRe.text.toString())){//패스워드/패스워드 확인이 일치
                if(localDB.checkIdExist(binding.regEditId.text.toString())){// 아이디 중복 확인
                    Toast.makeText(this,"아이디가 이미 존재합니다.", Toast.LENGTH_LONG).show()
                }else{// 존재하는 아이디
                    localDB.registerUser(binding.regEditId.text.toString(),binding.regEditPw.text.toString(),binding.writeName.text.toString(),
                        binding.writeNumber.text.toString(),binding.writeJoin.text.toString())
                }
            }else{ // 패스워드/패스워드 확인이 일치하지 않음
                Toast.makeText(this,"패스워드가 틀렸습니다.", Toast.LENGTH_LONG).show()
            }
        }
        val intent = Intent(this, LoginActivity::class.java )
        startActivity(intent)

    }

    val spinner1: Spinner = findViewById(R.id.join_spinner1)
// Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter.createFromResource(
        this,
        R.array.join_array1,
        android.R.layout.simple_spinner_item
    ).also { adapter ->
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner1.adapter = adapter
    }


    val spinner2: Spinner = findViewById(R.id.join_spinner2)
// Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter.createFromResource(
        this,
        R.array.join_array2,
        android.R.layout.simple_spinner_item 시발련아        존나이상해
    ).also { adapter ->
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner2.adapter = adapter
    }
}

 */
}

 */