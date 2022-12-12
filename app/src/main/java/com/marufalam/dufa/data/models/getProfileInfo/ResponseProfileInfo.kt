package com.marufalam.dufa.data.models.getProfileInfo


import com.google.gson.annotations.SerializedName

data class ResponseProfileInfo(
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)