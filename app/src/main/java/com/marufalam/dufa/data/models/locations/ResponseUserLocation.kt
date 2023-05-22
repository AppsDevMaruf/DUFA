package com.marufalam.dufa.data.models.locations


import com.google.gson.annotations.SerializedName

data class ResponseUserLocation(
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("userInfo")
    val userInfo: UserInfo?
) {
    data class Data(
        @SerializedName("address")
        val address: String?,
        @SerializedName("cityName")
        val cityName: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("email_verified_at")
        val emailVerifiedAt: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("latitude")
        val latitude: Double,
        @SerializedName("longitude")
        val longitude: Double,
        @SerializedName("name")
        val name: String?,
        @SerializedName("phone")
        val phone: String?,
        @SerializedName("profile_pic")
        val profilePic: Any?,
        @SerializedName("role")
        val role: String?,
        @SerializedName("status")
        val status: Int?,
        @SerializedName("updated_at")
        val updatedAt: String?,
        @SerializedName("user_id")
        val userId: Int?
    )

    data class UserInfo(
        @SerializedName("cityName")
        val cityName: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("latitude")
        val latitude: String?,
        @SerializedName("longitude")
        val longitude: String?,
        @SerializedName("updated_at")
        val updatedAt: String?,
        @SerializedName("user_id")
        val userId: Int?
    )
}