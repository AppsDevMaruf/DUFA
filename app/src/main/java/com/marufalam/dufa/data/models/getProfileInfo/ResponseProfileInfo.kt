package com.marufalam.dufa.data.models.getProfileInfo


import com.google.gson.annotations.SerializedName

data class ResponseProfileInfo(
    @SerializedName("profile")
    val profile: List<Profile?>?
)