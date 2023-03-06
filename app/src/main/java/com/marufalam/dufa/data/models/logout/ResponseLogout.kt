package com.marufalam.dufa.data.models.logout


import com.google.gson.annotations.SerializedName

data class ResponseLogout(
    @SerializedName("message")
    val message: String
)