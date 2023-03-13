package com.marufalam.dufa.data.models.getProfileInfo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseProfileInfo(
    @SerializedName("address")
    val address: String?,
    @SerializedName("birthdate")
    val birthdate: String?,
    @SerializedName("bloodgroup")
    val bloodgroup: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("district")
    val district: String?,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("hall")
    val hall: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_path")
    val imagePath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("nid")
    val nid: String?,
    @SerializedName("occupation")
    val occupation: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("profile_pic")
    val profilePic: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("subscription")
    val subscription: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
):Parcelable