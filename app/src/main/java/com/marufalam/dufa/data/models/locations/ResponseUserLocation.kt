package com.marufalam.dufa.data.models.locations


import com.google.gson.annotations.SerializedName

data class ResponseUserLocation(
    @SerializedName("allMembers")
    val allMembers: List<AllMember?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("user")
    val user: User?
) {
    data class AllMember(
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
        val email: String?,
        @SerializedName("gender")
        val gender: String?,
        @SerializedName("hall")
        val hall: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image_path")
        val imagePath: String?,
        @SerializedName("latitude")
        val latitude: Double?,
        @SerializedName("longitude")
        val longitude: Double?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("nickname")
        val nickname: String?,
        @SerializedName("nid")
        val nid: String?,
        @SerializedName("occupation")
        val occupation: String?,
        @SerializedName("phone")
        var phone: String?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("subscription")
        val subscription: String?,
        @SerializedName("updated_at")
        val updatedAt: String?
    )

    data class User(
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
        val email: String?,
        @SerializedName("gender")
        val gender: String?,
        @SerializedName("hall")
        val hall: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image_path")
        val imagePath: String?,
        @SerializedName("latitude")
        val latitude: Double?,
        @SerializedName("longitude")
        val longitude: Double?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("nickname")
        val nickname: Any?,
        @SerializedName("nid")
        val nid: String?,
        @SerializedName("occupation")
        val occupation: String?,
        @SerializedName("phone")
        val phone: String?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("subscription")
        val subscription: String?,
        @SerializedName("updated_at")
        val updatedAt: String?
    )
}