package com.marufalam.dufa.data.models.login


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("name")
    val name: String?,
    @SerializedName("token")
    val token: String?
)