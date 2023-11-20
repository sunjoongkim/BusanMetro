package com.example.busanmetro.data

import com.google.gson.annotations.SerializedName

data class ConResponse (
    @SerializedName("response") val response: ConResponseSub,
)

data class ConResponseSub (
    @SerializedName("body") val body: ConBody,
)

data class ConBody (
    @SerializedName("item") val items: List<ConItem>,
)

data class ConItem (
    @SerializedName("sname") val name: String,
    @SerializedName("wl_i") val wl_i: String,
    @SerializedName("wl_o") val wl_o: String,
    @SerializedName("el_i") val el_i: String,
    @SerializedName("el_o") val el_o: String,
    @SerializedName("es") val es: String,
    @SerializedName("blindroad") val blindroad: String,
    @SerializedName("ourbridge") val ourbridge: String,
    @SerializedName("helptake") val helptake: String,
    @SerializedName("toilet") val toilet: String,
    @SerializedName("toilet_gubun") val toilet_gubun: String,
)

