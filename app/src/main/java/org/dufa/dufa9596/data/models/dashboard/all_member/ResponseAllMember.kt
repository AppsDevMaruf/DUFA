package org.dufa.dufa9596.data.models.dashboard.all_member


import com.google.gson.annotations.SerializedName

data class ResponseAllMember(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("links")
    val links: Links?,
    @SerializedName("meta")
    val meta: Meta?
) {
    data class Data(
        @SerializedName("members")
        val members: List<Member?>?,
        @SerializedName("message")
        val message: String?,
        @SerializedName("status")
        val status: Int?,
        @SerializedName("success")
        val success: Boolean?
    ) {
        data class Member(
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
            val latitude: String?,
            @SerializedName("longitude")
            val longitude: String?,
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
    }

    data class Links(
        @SerializedName("first")
        val first: String?,
        @SerializedName("last")
        val last: String?,
        @SerializedName("next")
        val next: String?,
        @SerializedName("prev")
        val prev: Any?
    )

    data class Meta(
        @SerializedName("current_page")
        val currentPage: Int?,
        @SerializedName("from")
        val from: Int?,
        @SerializedName("last_page")
        val lastPage: Int?,
        @SerializedName("links")
        val links: List<Link?>?,
        @SerializedName("path")
        val path: String?,
        @SerializedName("per_page")
        val perPage: Int?,
        @SerializedName("to")
        val to: Int?,
        @SerializedName("total")
        val total: Int?
    ) {
        data class Link(
            @SerializedName("active")
            val active: Boolean?,
            @SerializedName("label")
            val label: String?,
            @SerializedName("url")
            val url: String?
        )
    }
}