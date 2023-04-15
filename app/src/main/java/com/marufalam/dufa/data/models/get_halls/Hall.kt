package com.marufalam.dufa.data.models.get_halls


import com.google.gson.annotations.SerializedName

data class Hall(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("updated_at")
    val updatedAt: Any?
)