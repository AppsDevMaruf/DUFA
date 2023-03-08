package com.marufalam.dufa.data.models.dashboard.dasboard_info


import com.google.gson.annotations.SerializedName

data class AuthInfo(
    @SerializedName("address")
    val address: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String?,
    @SerializedName("id")
    val id: Int?,
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
    val updatedAt: String?
)