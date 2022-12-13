package com.marufalam.dufa.data.models.dashboard


import com.google.gson.annotations.SerializedName

data class ResponseMemberList(
    @SerializedName("users")
    val users: List<User>)