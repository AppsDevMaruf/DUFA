package com.marufalam.dufa.data.models.dashboard.dasboard_info


import com.google.gson.annotations.SerializedName

data class ResponseMembersDashboardInfo(
    @SerializedName("allMembersData")
    val allMembersData: AllMembersData?,
    @SerializedName("authInfo")
    val authInfo: AuthInfo?,
    @SerializedName("dues")
    val dues: Int?,
    @SerializedName("memberInfo")
    val memberInfo: MemberInfo?
)