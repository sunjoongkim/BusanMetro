package com.example.busanmetro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.busanmetro.R.layout
import com.example.busanmetro.data.TimeItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime
import java.time.temporal.ChronoUnit


class StationInfoView : BottomSheetDialogFragment() {

    var name: String = ""
    var line: String = ""

    var itemsUp: List<TimeItem>? = null
    var itemsDown: List<TimeItem>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout.bottom_station_info_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textName: TextView = view.findViewById(R.id.text_name)
        val textUp1: TextView = view.findViewById(R.id.text_up_1)
        val textUp2: TextView = view.findViewById(R.id.text_up_2)
        val textDown1: TextView = view.findViewById(R.id.text_down_1)
        val textDown2: TextView = view.findViewById(R.id.text_down_2)

        var upCnt = 0
        var downCnt = 0

        Log.e("@@@@@", "====> itemsUp size : ${itemsUp?.size}")
        Log.e("@@@@@", "====> itemsDown size : ${itemsDown?.size}")

        textName.text = name

        if (itemsUp?.isEmpty() == true) {
            textUp1.text = "운행중인 열차가\n없습니다."
        } else {

            itemsUp?.forEach {
                val text = timeDifference(it.hour.toInt(), it.time.toInt()).toString() + "분 후 도착"

                if (upCnt == 0) {
                    textUp1.text = text
                    upCnt++
                } else {
                    textUp2.text = text
                }
            }
        }

        if (itemsDown?.isEmpty() == true) {
            textDown1.text = "운행중인 열차가\n없습니다."
        } else {
            itemsDown?.forEach {
                val text = timeDifference(it.hour.toInt(), it.time.toInt()).toString() + "분 후 도착"

                if (downCnt == 0) {
                    textDown1.text = text
                    downCnt++
                } else {
                    textDown2.text = text
                }
            }
        }


    }

    private fun timeDifference(targetHour: Int, targetMinute: Int): Long {
        val now = LocalTime.now()

        // 타겟 시간을 설정
        val targetTime = LocalTime.of(targetHour, targetMinute)

        // 타겟 시간이 현재 시간 이후라면 차이를 계산
        // 그렇지 않으면 0을 반환
        return if (targetTime.isAfter(now)) {
            ChronoUnit.MINUTES.between(now, targetTime)
        } else {
            0
        }
    }
}