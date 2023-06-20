package com.example.Sseudam

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import java.util.jar.Manifest

class QrcodescanActivity : AppCompatActivity() {

    lateinit var qrScanner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcodescan)
        val intent = intent
        var name = intent.getStringExtra("name")
        var nameid = findViewById<TextView>(R.id.nameid)
        name = "${nameid}"

        //qr권한 체크
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 123)
        }else{
            //QRCODE 스캔
            scan()
        }

        //리셋버튼 이벤트
        val qr_resetBtn: ImageButton = findViewById(R.id.qr_resetBtn)
        qr_resetBtn.setOnClickListener{
            //초기화 확인하고 실행
            if (::qrScanner.isInitialized){
                qrScanner.startPreview()
            }
        }


    }

    //QRCODE 스캔
    private fun scan(){
        //초기화
        val scannerView: CodeScannerView = findViewById(R.id.qr_scannerView)

        qrScanner = CodeScanner(this, scannerView)
        //스캐너 셋팅
        qrScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS //포맷

            autoFocusMode = AutoFocusMode.SAFE //포커스
            isAutoFocusEnabled = true //자동 포커스 활성화
            isFlashEnabled = false //플래쉬

            //QRCODE 확인되면 실행
            decodeCallback = DecodeCallback {
                runOnUiThread {
                    var name = intent.getStringExtra("name")
                    var nameid = findViewById<TextView>(R.id.nameid)
                    name = "${nameid}"
                    val intent = Intent(applicationContext, BluetoothActivity::class.java)
                    intent.putExtra("name", name)
                    startActivity(intent)
                }
            }
            //에러 발생 시 실행
            qrScanner.errorCallback = ErrorCallback {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Scan Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //스캔뷰 클릭 이벤트
        scannerView.setOnClickListener{
            //초기화 확인하고 실행
            if (::qrScanner.isInitialized){
                qrScanner.startPreview()
            }
        }
    }

    //권한요청 처리결과
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext, "카메라 권한 부여됨", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "카메라 권한 거부됨", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //액티비티 재실행 되면 실행됨
    override fun onResume() {
        super.onResume()
        //초기화 확인하고 실행
        if (::qrScanner.isInitialized){
            qrScanner.startPreview()
        }
    }

    //액티비티 정지되면 실행됨
    override fun onPause() {
        super.onPause()
        //초기화 확인하고 실행
        if (::qrScanner.isInitialized){
            qrScanner.releaseResources()
        }
    }
}