package com.example.busanmetro

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.busanmetro.databinding.ActivityConvenienceBinding
import com.example.busanmetro.service.RestApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConvenienceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConvenienceBinding

    private val restApiService = RestApiService.instance

    var scode: Int = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConvenienceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scode = intent.getIntExtra("scode", 101)

        initData()
    }

    private fun initData() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.e("@@@@@", "====> scode : $scode")
            val response = restApiService.getConvenienceInfo(RestApiService.serviceKey, "json", scode)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.e("@@@@@", "=====> body : $body")
                    val itemList = body?.response?.body?.items

                    val data = itemList?.get(0)

                    binding.textName.text = data?.name
                    binding.text1.text = data?.wl_i
                    binding.text2.text = data?.wl_o
                    binding.text3.text = data?.el_i
                    binding.text4.text = data?.el_o
                    binding.text5.text = data?.es
                    binding.text6.text = data?.blindroad
                    binding.text7.text = data?.ourbridge
                    binding.text8.text = data?.helptake
                    binding.text9.text = data?.toilet
                    binding.text10.text = data?.toilet_gubun

                } else {
                    Log.e("@@@@@", "=====> FAIL!!")
                }
            }
        }

    }

}