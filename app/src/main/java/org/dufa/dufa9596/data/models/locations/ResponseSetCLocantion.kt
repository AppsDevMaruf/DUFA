package org.dufa.dufa9596.data.models.locations


import com.google.gson.annotations.SerializedName

data class ResponseSetCLocantion(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)