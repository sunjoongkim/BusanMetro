package com.example.busanmetro

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.busanmetro.data.TimeItem
import com.example.busanmetro.databinding.ActivityMainBinding
import com.example.busanmetro.service.RestApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var stationMap = HashMap<View, Int>()

    private val restApiService = RestApiService.instance

    private var selectedStation: ImageView? = null

    private var itemsUp: MutableList<TimeItem> = mutableListOf()
    private var itemsDown: MutableList<TimeItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutLine1.visibility = View.VISIBLE
        binding.layoutLine2.visibility = View.VISIBLE
        binding.layoutLine3.visibility = View.VISIBLE
        binding.layoutLine4.visibility = View.VISIBLE
        binding.layoutLine5.visibility = View.VISIBLE
        binding.layoutLine6.visibility = View.VISIBLE

        initStationMap()
        addClickListener()
    }

    private fun addClickListener() {
        val station1 = binding.layoutLine1

        for (i in 0 until station1.childCount) {
            val child = station1.getChildAt(i)
            child.setOnClickListener(onClickListener)
        }

        val station2 = binding.layoutLine2

        for (i in 0 until station2.childCount) {
            val child = station2.getChildAt(i)
            child.setOnClickListener(onClickListener)
        }

        val station3 = binding.layoutLine3

        for (i in 0 until station3.childCount) {
            val child = station3.getChildAt(i)
            child.setOnClickListener(onClickListener)
        }

        val station4 = binding.layoutLine4

        for (i in 0 until station4.childCount) {
            val child = station4.getChildAt(i)
            child.setOnClickListener(onClickListener)
        }
    }

    private val onClickListener = View.OnClickListener { view ->


        // stationMap 맵에서 선택한 ImageView에 해당되는 역 코드를 가져옴
        val station = stationMap.get(view)

        var name = ""

        // 클릭시 빨간색 표시 처리
        if (selectedStation != null) {
            selectedStation!!.isSelected = false
        }

        selectedStation = view as ImageView
        view.isSelected = true

        // 클릭한 시간으로 부터 day, time 변수 설정
        val (time, day) = getCurrentTimeAndDay()


        Log.e("@@@@@", "=======> restApiService $restApiService")
        Log.e("@@@@@", "=======> station $station")
        Log.e("@@@@@", "=======> day $day")
        Log.e("@@@@@", "=======> time $time")

        CoroutineScope(Dispatchers.IO).launch {
            // 역 정보
            val stationResponse = restApiService.getStationInfo(RestApiService.serviceKey, "json", station!!)
            withContext(Dispatchers.Main) {
                if (stationResponse.isSuccessful) {
                    val body = stationResponse.body()
                    Log.e("@@@@@", "=====> stationResponse body : $body")
                    val item = body?.response?.body?.item

                    name = item?.name ?: "정보없음"
                } else {
                    Log.e("@@@@@", "=====> FAIL!!")
                }
            }



            // 상행선 열차 2개
            val upResponse = restApiService.getTimeInfo(RestApiService.serviceKey, "json", station!!, day, time, 0, 2)
            withContext(Dispatchers.Main) {
                if (upResponse.isSuccessful) {
                    val body = upResponse.body()
                    Log.e("@@@@@", "=====> body : $body")
                    val itemList = body?.response?.body?.items

                    itemsUp.addAll(itemList!!)
                } else {
                    Log.e("@@@@@", "=====> FAIL!!")
                }
            }

            // 하행선 열차 2개
            val downResponse = restApiService.getTimeInfo(RestApiService.serviceKey, "json", station!!, day, time, 1, 2)
            withContext(Dispatchers.Main) {
                if (downResponse.isSuccessful) {
                    val body = downResponse.body()
                    Log.e("@@@@@", "=====> body : $body")
                    val itemList = body?.response?.body?.items

                    itemsDown.addAll(itemList!!)
                } else {
                    Log.e("@@@@@", "=====> FAIL!!")
                }
            }

            showStationInfo(name)
        }

    }

    private fun getCurrentTimeAndDay(): Pair<String, Int> {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        val time = String.format("%02d%02d", hour, minute);

        val day = when (dayOfWeek) {
            Calendar.SATURDAY -> 2
            Calendar.SUNDAY -> 3
            else -> 1 // 평일
        }

        return Pair(time, day)
    }

    private fun showStationInfo(name: String) {
        val stationInfoView = StationInfoView()
        stationInfoView.name = name
        stationInfoView.itemsUp = itemsUp
        stationInfoView.itemsDown = itemsDown
        stationInfoView.show(supportFragmentManager, stationInfoView.tag)
    }

    private fun initStationMap() {

        // 1호선
        stationMap.put(binding.btn11, 134)
        stationMap.put(binding.btn12, 133)
        stationMap.put(binding.btn13, 132)
        stationMap.put(binding.btn14, 131)
        stationMap.put(binding.btn15, 130)
        stationMap.put(binding.btn16, 129)
        stationMap.put(binding.btn17, 128)
        stationMap.put(binding.btn18, 127)
        stationMap.put(binding.btn19, 126)
        stationMap.put(binding.btn110, 125)
        stationMap.put(binding.btn111, 124)
        stationMap.put(binding.btn112, 123)
        stationMap.put(binding.btn113, 122)
        stationMap.put(binding.btn114, 121)
        stationMap.put(binding.btn115, 120)
        stationMap.put(binding.btn116, 118)
        stationMap.put(binding.btn117, 117)
        stationMap.put(binding.btn118, 116)
        stationMap.put(binding.btn119, 115)
        stationMap.put(binding.btn120, 114)
        stationMap.put(binding.btn121, 113)
        stationMap.put(binding.btn122, 112)
        stationMap.put(binding.btn123, 111)
        stationMap.put(binding.btn124, 110)
        stationMap.put(binding.btn125, 109)
        stationMap.put(binding.btn126, 108)
        stationMap.put(binding.btn127, 107)
        stationMap.put(binding.btn128, 106)
        stationMap.put(binding.btn129, 105)
        stationMap.put(binding.btn130, 104)
        stationMap.put(binding.btn131, 103)
        stationMap.put(binding.btn132, 102)
        stationMap.put(binding.btn133, 101)
        stationMap.put(binding.btn134, 100)
        stationMap.put(binding.btn135, 99)
        stationMap.put(binding.btn136, 98)
        stationMap.put(binding.btn137, 97)
        stationMap.put(binding.btn138, 96)
        stationMap.put(binding.btn139, 95)

        // 2호선
        stationMap.put(binding.btn21, 243)
        stationMap.put(binding.btn22, 242)
        stationMap.put(binding.btn23, 241)
        stationMap.put(binding.btn24, 240)
        stationMap.put(binding.btn25, 239)
        stationMap.put(binding.btn26, 238)
        stationMap.put(binding.btn27, 237)
        stationMap.put(binding.btn28, 236)
        stationMap.put(binding.btn29, 235)
        stationMap.put(binding.btn210, 234)
        stationMap.put(binding.btn211, 233)
        stationMap.put(binding.btn212, 232)
        stationMap.put(binding.btn213, 231)
        stationMap.put(binding.btn214, 230)
        stationMap.put(binding.btn215, 229)
        stationMap.put(binding.btn216, 228)
        stationMap.put(binding.btn217, 227)
        stationMap.put(binding.btn218, 226)
        stationMap.put(binding.btn219, 225)
        stationMap.put(binding.btn220, 224)
        stationMap.put(binding.btn221, 223)
        stationMap.put(binding.btn222, 222)
        stationMap.put(binding.btn223, 221)
        stationMap.put(binding.btn224, 220)
        stationMap.put(binding.btn225, 219)
        stationMap.put(binding.btn226, 218)
        stationMap.put(binding.btn227, 217)
        stationMap.put(binding.btn228, 216)
        stationMap.put(binding.btn229, 215)
        stationMap.put(binding.btn230, 214)
        stationMap.put(binding.btn231, 213)
        stationMap.put(binding.btn232, 212)
        stationMap.put(binding.btn233, 211)
        stationMap.put(binding.btn234, 210)
        stationMap.put(binding.btn235, 209)
        stationMap.put(binding.btn236, 208)
        stationMap.put(binding.btn237, 207)
        stationMap.put(binding.btn238, 206)
        stationMap.put(binding.btn239, 205)
        stationMap.put(binding.btn240, 204)
        stationMap.put(binding.btn241, 203)
        stationMap.put(binding.btn242, 202)
        stationMap.put(binding.btn243, 201)

        // 3호선
        stationMap.put(binding.btn31, 316)
        stationMap.put(binding.btn32, 315)
        stationMap.put(binding.btn33, 314)
        stationMap.put(binding.btn34, 312)
        stationMap.put(binding.btn35, 311)
        stationMap.put(binding.btn36, 310)
        stationMap.put(binding.btn37, 309)
        stationMap.put(binding.btn38, 308)
        stationMap.put(binding.btn39, 307)
        stationMap.put(binding.btn310, 306)
        stationMap.put(binding.btn311, 304)
        stationMap.put(binding.btn312, 303)
        stationMap.put(binding.btn313, 302)

        // 4호선
        stationMap.put(binding.btn41, 414)
        stationMap.put(binding.btn42, 413)
        stationMap.put(binding.btn43, 412)
        stationMap.put(binding.btn44, 411)
        stationMap.put(binding.btn45, 410)
        stationMap.put(binding.btn46, 409)
        stationMap.put(binding.btn47, 408)
        stationMap.put(binding.btn48, 407)
        stationMap.put(binding.btn49, 406)
        stationMap.put(binding.btn410, 405)
        stationMap.put(binding.btn411, 404)
        stationMap.put(binding.btn412, 403)

    }
}