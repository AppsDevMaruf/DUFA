package com.marufalam.dufa.models

data class LoginResponse(
    val `data`: Data,
    val message: String,
    val token: String
)