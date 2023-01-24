package com.marufalam.dufa.data.models.dashboard


import com.google.gson.annotations.SerializedName

data class ResponseAllMember(
    @SerializedName("allmembers")
    val allmembers: List<Allmember>
)