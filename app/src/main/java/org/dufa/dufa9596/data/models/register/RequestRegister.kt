package org.dufa.dufa9596.data.models.register


import com.google.gson.annotations.SerializedName

data class RequestRegister(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("confirm_password")
    val confirmPassword: String

)