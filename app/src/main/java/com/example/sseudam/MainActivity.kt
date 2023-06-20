package com.example.Sseudam

import User
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.Sseudam.databinding.ActivityLoginBinding
import com.example.Sseudam.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.security.MessageDigest
import kotlin.math.floor

class MainActivity : AppCompatActivity() {
    /*
    private val HomeFragment by lazy { HomeFragment() }
    private val LankFragment by lazy { LankFragment() }
    private val ShopFragment by lazy { ShopFragment() }
    private val MypageFragment by lazy { MypageFragment() }

 */
    private lateinit var binding: ActivityMainBinding
    var point:Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val intent = intent
        var name = intent.getStringExtra("name")
        var getPoint = intent.getDoubleExtra("point",0.0)
        binding.nameid.text = "${name}"
        binding.pointText.text = "${point}"

        binding.mainBottombar.setOnItemSelectedListener {

            when(it.itemId){


                R.id.bottom_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("name", binding.nameid.text.toString())
                    intent.putExtra("point",binding.pointText.text.toString())
                    startActivity(intent)
                }
                R.id.bottom_shop -> {
                    val intent = Intent(this, ShopActivity::class.java)
                    intent.putExtra("name", binding.nameid.text.toString())
                    intent.putExtra("point",binding.pointText.text.toString())
                    startActivity(intent)
                }
                R.id.bottom_my -> {
                    val intent = Intent(this, MypageActivity::class.java)
                    intent.putExtra("name", binding.nameid.text.toString())
                    intent.putExtra("point",binding.pointText.text.toString())
                    startActivity(intent)
                }

            }
            true
        }

        val mainBottombar: Menu = binding.mainBottombar.getMenu()
        mainBottombar.setGroupCheckable(0, true, false)
        mainBottombar.findItem(R.id.bottom_home).setChecked(true)
        mainBottombar.findItem(R.id.bottom_shop).setChecked(false)
        mainBottombar.findItem(R.id.bottom_my).setChecked(false)


        binding.mainMapButton.setOnClickListener{
            val intent = Intent(this, MapAcitivity::class.java)
            startActivity(intent)
        }
        binding.Scan.setOnClickListener {
            intent.putExtra("name", binding.nameid.text.toString())
            val intent = Intent(this, QrcodescanActivity::class.java)
            startActivity(intent)
        }

        if(getPoint == 0.0){
            binding.pointText.setText("0")
        }else if (getPoint != 0.0){
            point += getPoint
           binding.pointText.setText(floor(point).toInt().toString())
        }
    }





}


    /*
    private fun NavigationClick() {
        val main_bottombar = findViewById<BottomNavigationView>(R.id.main_bottombar)

        main_bottombar.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.bottom_home -> {

                        changeFragment(HomeFragment)
                    }
                    R.id.bottom_lank -> {
                        changeFragment(LankFragment)
                    }
                    R.id.bottom_shop -> {
                        changeFragment(ShopFragment)
                    }
                    R.id.bottom_my -> {
                        changeFragment(MypageFragment)
                    }
                }
                true
            }
            selectedItemId = R.id.bottom_home
        }

    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_FragmentContainer, fragment)
            .commit()
    }

     */



