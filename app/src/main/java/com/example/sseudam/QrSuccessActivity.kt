package com.example.Sseudam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import com.example.Sseudam.R
import kotlin.math.log


class QrSuccessActivity : AppCompatActivity() {
    var bt: BluetoothSPP? = BluetoothSPP(this)
    var Gram:Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_success)
        val ssedamName = intent.getStringExtra("ssedamName")
        val qr_success_text: TextView = findViewById(R.id.qr_success_text)
        var name = intent.getStringExtra("name")
        qr_success_text.setText(ssedamName)
        bt!!.setOnDataReceivedListener(object : BluetoothSPP.OnDataReceivedListener {
            val husky_messege: TextView = findViewById(R.id.husky_messege)
            //val gram: TextView = findViewById(R.id.gram)
            override fun onDataReceived(data: ByteArray?, message: String?) {
                intent.putExtra("name", name)
                Log.d("허스키렌즈", "${message.toString()}")
                if (message == "0"){
                    husky_messege.setText("앞에 상자를 열어 쓰레기를 인식 시켜주세요.")
                }else if (message == "1"){
                    husky_messege.setText("페트병 라벨을 제거해 주세요.")
                }else if (message == "2"){
                    husky_messege.setText("상단 페트병 입구가 열립니다.")
                }else if (message == "3"){
                    husky_messege.setText("하단 캔 입구가 열립니다.")
                }
                else{
                    Gram = message!!.toDouble()
              //      gram.setText(Gram.toString())
                }

            }
        })
    }
}