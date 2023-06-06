package org.dufa.dufa9596.data.models.search.blood


import com.google.gson.annotations.SerializedName

data class Bloodgroup(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("updated_at")
    val updatedAt: Any?
)