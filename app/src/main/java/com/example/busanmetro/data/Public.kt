package com.example.busanmetro.data

import com.google.gson.annotations.SerializedName


data class PublicResponse (
    @SerializedName("response") val response: PublicResponseSub,
)

data class PublicResponseSub (
    @SerializedName("body") val body: PublicBody,
)

data class PublicBody (
    @SerializedName("item") val items: List<PublicItem>,
)

data class PublicItem(
    @SerializedName("sname") val name: String,
    @SerializedName("bicycle_location") val bicycle_location: String,
    @SerializedName("bicycle_management") val bicycle_management: String,
    @SerializedName("bicycle_ea") val bicycle_ea: String,
    @SerializedName("parking") val parking: String,
    @SerializedName("parking_dimension") val parking_dimension: String,
    @SerializedName("parking_tel") val parking_tel: String,
    @SerializedName("cabinet_s") val cabinet_s: String,
    @SerializedName("cabinet_m") val cabinet_m: String,
    @SerializedName("cabinet_l") val cabinet_l: String,
    @SerializedName("cabinet_cost") val cabinet_cost: String,
    @SerializedName("meet") val meet: String,
    @SerializedName("atm") val atm: String,
    @SerializedName("atm_locational") val atm_locational: String,
)