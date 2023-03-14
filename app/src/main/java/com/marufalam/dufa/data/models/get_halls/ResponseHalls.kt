package com.marufalam.dufa.data.models.get_halls


import com.google.gson.annotations.SerializedName

data class ResponseHalls(
    @SerializedName("halls")
    val halls: List<Hall?>?
)