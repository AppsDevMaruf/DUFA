package com.marufalam.dufa.data.models.getProfileInfo


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: Any?,
    @SerializedName("birthdate")
    val birthdate: String?,
    @SerializedName("bloodgroup")
    val bloodgroup: Any?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("district")
    val district: Any?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("hall")
    val hall: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image_path")
    val imagePath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("nid")
    val nid: String?,
    @SerializedName("occupation")
    val occupation: Any?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("subscription")
    val subscription: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)