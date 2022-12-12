package com.marufalam.dufa.data.models.login

data class ResponseLogin(
    val `data`: Data,
    val message: String,
    val token: String
)