package com.example.busanmetro.data

import com.google.gson.annotations.SerializedName


data class StationResponse (
    @SerializedName("response") val response: StationResponseSub,
)

data class StationResponseSub (
    @SerializedName("body") val body: StationBody,
)

data class StationBody (
    @SerializedName("item") val item: StationItem,
)

data class StationItem (
    @SerializedName("sname") val name: String
)