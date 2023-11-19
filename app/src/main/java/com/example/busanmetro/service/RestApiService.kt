package com.example.busanmetro.service

import com.example.busanmetro.data.StationResponse
import com.example.busanmetro.data.TimeResponse
import com.google.gson.GsonBuilder
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

interface RestApiService {

    companion object {
        val instance = RestApiServiceGenerator.createService(RestApiService::class.java)
        val serviceKey = "6Mf7JU80N0S5ZbivsHzo13mlfkxuBwi7hKcKAHQZ+3QZlf8g01OoTn0Loiz0QhZCvQd6PmZhUFyM6KZXGiNxbQ=="
    }

    @GET("open_api_process.tnn")
    suspend fun getTimeInfo(
        @Query("serviceKey") serviceKey: String,
        @Query("act") type: String,
        @Query("scode") code: Int,
        @Query("day") day: Int,
        @Query("stime") time: String,
        @Query("updown") updown: Int,
        @Query("enum") enum: Int
    ) : Response<TimeResponse>

    @GET("open_api_stationinfo.tnn")
    suspend fun getStationInfo(
        @Query("serviceKey") serviceKey: String,
        @Query("act") type: String,
        @Query("scode") code: Int,
    ) : Response<StationResponse>
}

object RestApiServiceGenerator {

    private const val BASE_URL = "http://data.humetro.busan.kr/voc/api/"

    fun <S> createService(serviceClass: Class<S>): S {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        val myCookieJar = JavaNetCookieJar(cookieManager)

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .cookieJar(myCookieJar)
            .retryOnConnectionFailure(true)
            .build()

        val gson = GsonBuilder().setLenient().create()

        val builder = Retrofit.Builder()
            //.addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
        val retrofit = builder.build()
        return retrofit.create(serviceClass)
    }

}