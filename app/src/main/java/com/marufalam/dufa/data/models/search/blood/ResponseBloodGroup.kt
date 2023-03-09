package com.marufalam.dufa.data.models.search.blood


import com.google.gson.annotations.SerializedName

data class ResponseBloodGroup(
    @SerializedName("bloodgroups")
    val bloodgroups: List<Bloodgroup?>?
)