package com.example.Sseudam

import User
import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.Sseudam.databinding.ActivityMapAcitivityBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import net.daum.mf.map.api.*


class MapAcitivity : AppCompatActivity(), MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {
    private var mMapView: MapView? = null
    var binding: ActivityMapAcitivityBinding? = null
    var REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    lateinit var db: DatabaseHelper
    var users = ArrayList<User>()
//    private val eventListener = MarkerEventListener(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        db = DatabaseHelper(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_acitivity)
        mMapView = findViewById<View>(R.id.map_view) as MapView
        //mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        mMapView!!.setCurrentLocationEventListener(this)

        val intent = intent
        var name = intent.getStringExtra("name")

        mMapView!!.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater))
        //mMapView!!.setPOIItemEventListener(eventListener)

        // 마커 추가
        val marker = MapPOIItem()
        marker.apply {
            itemName = "쓰담1" // 마커 이름
            mapPoint = MapPoint.mapPointWithGeoCoord(35.15093973014711, 126.76952446826427) // 좌표
            markerType = MapPOIItem.MarkerType.CustomImage // 마커 모양 (커스텀)
            customImageResourceId = R.drawable.map_marker_noclick // 커스텀 마커 이미지
            selectedMarkerType = MapPOIItem.MarkerType.CustomImage // 클릭 시 마커 모양 (커스텀)
            customSelectedImageResourceId = R.drawable.map_marker_click // 클릭 시 커스텀 마커 이미지
            isCustomImageAutoscale = true      // 커스텀 마커 이미지 크기 자동 조정
            setCustomImageAnchor(0.5f, 1.0f)    // 마커 이미지 기준점
        }
        mMapView!!.addPOIItem(marker)


        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting()
        } else {
            checkRunTimePermission()
        }


        val goScan = findViewById<Button>(R.id.goScan)
        goScan.setOnClickListener {
            val intent = Intent(this, QrcodescanActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        mMapView!!.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOff
        mMapView!!.setShowCurrentLocationMarker(false)
    }

    override fun onCurrentLocationUpdate(
        mapView: MapView,
        currentLocation: MapPoint,
        accuracyInMeters: Float
    ) {
        val mapPointGeo = currentLocation.mapPointGeoCoord
        Log.i(
            LOG_TAG,
            String.format(
                "MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)",
                mapPointGeo.latitude,
                mapPointGeo.longitude,
                accuracyInMeters
            )
        )
    }

    override fun onCurrentLocationDeviceHeadingUpdate(mapView: MapView, v: Float) {}
    override fun onCurrentLocationUpdateFailed(mapView: MapView) {}
    override fun onCurrentLocationUpdateCancelled(mapView: MapView) {}
    override fun onReverseGeoCoderFoundAddress(mapReverseGeoCoder: MapReverseGeoCoder, s: String) {
        mapReverseGeoCoder.toString()
        onFinishReverseGeoCoding(s)
    }

    override fun onReverseGeoCoderFailedToFindAddress(mapReverseGeoCoder: MapReverseGeoCoder) {
        onFinishReverseGeoCoding("Fail")
    }

    private fun onFinishReverseGeoCoding(result: String) {
//        Toast.makeText(LocationDemoActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }

    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    override fun onRequestPermissionsResult(
        permsRequestCode: Int,
        permissions: Array<String>,
        grandResults: IntArray
    ) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults)
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.size == REQUIRED_PERMISSIONS.size) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            var check_result = true


            // 모든 퍼미션을 허용했는지 체크합니다.
            for (result in grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false
                    break
                }
            }
            if (check_result) {
                Log.d("@@@", "start")
                //위치 값을 가져올 수 있음
                mMapView!!.currentLocationTrackingMode =
                    MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        REQUIRED_PERMISSIONS[0]
                    )
                ) {
                    Toast.makeText(
                        this,
                        "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    fun checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음
            mMapView!!.currentLocationTrackingMode =
                MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    REQUIRED_PERMISSIONS[0]
                )
            ) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG)
                    .show()
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private fun showDialogForLocationServiceSetting() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            """
    앱을 사용하기 위해서는 위치 서비스가 필요합니다.
    위치 설정을 수정하실래요?
    """.trimIndent()
        )
        builder.setCancelable(true)
        builder.setPositiveButton("설정") { dialog, id ->
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE)
        }
        builder.setNegativeButton("취소") { dialog, id -> dialog.cancel() }
        builder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GPS_ENABLE_REQUEST_CODE ->
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음")
                        checkRunTimePermission()
                        return
                    }
                }
        }
    }

    fun checkLocationServicesStatus(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    companion object {
        private const val LOG_TAG = "MainActivity"
        private const val GPS_ENABLE_REQUEST_CODE = 2001
        private const val PERMISSIONS_REQUEST_CODE = 100
    }


    class CustomBalloonAdapter(inflater: LayoutInflater) : CalloutBalloonAdapter {
        val mCalloutBalloon: View = inflater.inflate(R.layout.balloon_layout, null)
        val name: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_name)
        val address: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_address)
        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            name.text = poiItem?.itemName
            address.text = "여유"
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {

            return mCalloutBalloon
        }

    }



    // 마커 클릭 이벤트 리스너

}
/*
class MarkerEventListener(val context: Context) : MapView.POIItemEventListener {
    override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
        // 마커 클릭 시
    }

    override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {
        // 말풍선 클릭 시 (Deprecated)
        // 이 함수도 작동하지만 그냥 아래 있는 함수에 작성하자
    }

    override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?, buttonType: MapPOIItem.CalloutBalloonButtonType?) {
        // 말풍선 클릭 시
        val builder = AlertDialog.Builder(context)
        builder.show()


    }

    override fun onDraggablePOIItemMoved(
        mapView: MapView?,
        poiItem: MapPOIItem?,
        mapPoint: MapPoint?
    ) {
        // 마커의 속성 중 isDraggable = true 일 때 마커를 이동시켰을 경우
    }

}

 */