package com.example.busanmetro.data

import com.google.gson.annotations.SerializedName

data class TimeResponse (
    @SerializedName("response") val response: TimeResponseSub,
)

data class TimeResponseSub (
    @SerializedName("body") val body: TimeBody,
)

data class TimeBody (
    @SerializedName("item") val items: List<TimeItem>,
)

data class TimeItem (
    @SerializedName("sname") val name: String,
    @SerializedName("hour") val hour: String,
    @SerializedName("time") val time: String,
    @SerializedName("trainno") val trainno: String,
    @SerializedName("updown") val updown: String,
)

