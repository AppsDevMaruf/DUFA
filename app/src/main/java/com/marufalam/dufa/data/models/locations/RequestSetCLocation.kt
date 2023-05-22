package com.marufalam.dufa.data.models.locations


import com.google.gson.annotations.SerializedName

data class RequestSetCLocation(
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("cityName")
    val cityName: String?,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?

)