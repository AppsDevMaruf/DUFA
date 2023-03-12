package com.marufalam.dufa.data.models.login

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("token")
    val token: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)