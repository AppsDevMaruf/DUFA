package com.marufalam.dufa.models.login

data class ResponseLogin(
    val `data`: Data,
    val message: String,
    val token: String
)