package org.dufa.dufa9596.data.models.fee_list


import com.google.gson.annotations.SerializedName

data class ResponseFeeList(
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
) {
    data class Data(
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("fee")
        val fee: Int?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("updated_at")
        val updatedAt: Any?
    )
}