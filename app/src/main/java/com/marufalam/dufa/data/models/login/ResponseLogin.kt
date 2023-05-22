package com.marufalam.dufa.data.models.login

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("token")
    val token: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("user_id")
    val user_id: Int)
