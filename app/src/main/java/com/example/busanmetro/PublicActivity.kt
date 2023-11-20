package com.example.busanmetro

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.busanmetro.databinding.ActivityPublicBinding
import com.example.busanmetro.service.RestApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PublicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublicBinding

    private val restApiService = RestApiService.instance

    var scode: Int = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPublicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scode = intent.getIntExtra("scode", 101)

        initData()
    }

    private fun initData() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = restApiService.getPublicInfo(RestApiService.serviceKey, "json", scode)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.e("@@@@@", "=====> body : $body")
                    val itemList = body?.response?.body?.items

                    val data = itemList?.get(0)

                    binding.textName.text = data?.name
                    binding.text1.text = data?.bicycle_location
                    binding.text2.text = data?.bicycle_management
                    binding.text3.text = data?.bicycle_ea
                    binding.text4.text = data?.parking
                    binding.text5.text = data?.parking_dimension
                    binding.text6.text = data?.parking_tel
                    binding.text7.text = data?.cabinet_s
                    binding.text8.text = data?.cabinet_m
                    binding.text9.text = data?.cabinet_l
                    binding.text10.text = data?.cabinet_cost
                    binding.text11.text = data?.meet
                    binding.text12.text = data?.atm
                    binding.text13.text = data?.atm_locational

                } else {
                    Log.e("@@@@@", "=====> FAIL!!")
                }
            }
        }

    }

}