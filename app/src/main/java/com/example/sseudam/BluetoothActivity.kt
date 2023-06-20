package com.example.Sseudam

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import app.akexorcist.bluetotohspp.library.DeviceList
import com.example.Sseudam.databinding.ActivityBluetoothBinding

class BluetoothActivity : AppCompatActivity() {
    var bt: BluetoothSPP? = null
    //추가
    var newGram:Double = 0.0
    var oldGram:Double = 0.0
    var dddddd:String = ""
    lateinit var binding: ActivityBluetoothBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBluetoothBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var name = intent.getStringExtra("name")
        bt = BluetoothSPP(this)



        if (!bt!!.isBluetoothAvailable){
            Toast.makeText(applicationContext, "블루투스 사용 불가",Toast.LENGTH_SHORT).show()
            finish()
        }

        bt!!.setOnDataReceivedListener(object : BluetoothSPP.OnDataReceivedListener { //데이터 수신
            val husky_messege: TextView = findViewById(R.id.husky_messege)
//            val gram:TextView = findViewById(R.id.gram)
            override fun onDataReceived(data: ByteArray?, message: String?) {

                Log.d("값받음,콘솔액티비티 :", data.toString())
                dddddd = message.toString()


                if (dddddd == "0") {
                    husky_messege.setText("앞에 상자를 열어 쓰레기를 인식 시켜주세요.")
                } else if (dddddd == "1") {
                    husky_messege.setText("페트병 라벨을 제거해 주세요.")
                } else if (dddddd == "2") {
                    husky_messege.setText("상단 페트병 입구가 열립니다.")
                }else if (dddddd == "3") {
                    husky_messege.setText("하단 캔 입구가 열립니다.")
                }//추가
                else{
                    dddddd = dddddd.substring(1)
                    Log.d("i!!!!!!ddddddf문newGram",dddddd)
                    newGram = dddddd.toDouble()

                }

            }
        })

        val completed:Button = findViewById(R.id.completed)
        completed.setOnClickListener {
            var CLACGRAM:Double = 0.0
            var name = intent.getStringExtra("name")
            if (newGram<0){
                newGram *= -1
            }
            if (oldGram != newGram){
                CLACGRAM = newGram - oldGram
                CLACGRAM.toInt()
            }
            oldGram = oldGram + CLACGRAM

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("point",CLACGRAM)
            intent.putExtra("name",name)
            startActivity(intent)
        }



        bt!!.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            //연결됐을 때
            override fun onDeviceConnected(name: String, address: String) {
                Toast.makeText(
                    applicationContext, "Connected to $name\n$address", Toast.LENGTH_SHORT).show()

            }

            override fun onDeviceDisconnected() { //연결해제
                Toast.makeText(applicationContext, "Connection lost", Toast.LENGTH_SHORT).show()
            }

            override fun onDeviceConnectionFailed() { //연결실패
                Toast.makeText(applicationContext, "Unable to connect", Toast.LENGTH_SHORT).show()
            }
        })

        val btnConnect = findViewById<Button>(R.id.btnConnect) //연결시도
        btnConnect.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                intent.putExtra("name", name)
                if (bt!!.serviceState == BluetoothState.STATE_CONNECTED) {
                    bt!!.disconnect()
                } else {
                    val intent = Intent(applicationContext, DeviceList::class.java)
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
                }
            }

        })
    }

    public override fun onDestroy() {
        super.onDestroy()
        bt!!.stopService() //블루투스 중지
    }

    public override fun onStart() {
        super.onStart()

        if (!bt!!.isBluetoothEnabled) { //
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT)
            }
        } else {
            if (!bt!!.isServiceAvailable) {
                bt!!.setupService()
                bt!!.startService(BluetoothState.DEVICE_OTHER) //DEVICE_ANDROID는 안드로이드 기기 끼리
//                setup()
            }
        }
    }



    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == RESULT_OK) bt!!.connect(data)
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                bt!!.setupService()
                bt!!.startService(BluetoothState.DEVICE_OTHER)
//                setup()
            } else {
                Toast.makeText(
                    applicationContext, "Bluetooth was not enabled.", Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
}