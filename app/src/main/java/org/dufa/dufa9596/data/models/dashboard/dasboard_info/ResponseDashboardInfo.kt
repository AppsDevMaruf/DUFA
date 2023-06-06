package org.dufa.dufa9596.data.models.dashboard.dasboard_info


import com.google.gson.annotations.SerializedName

data class ResponseDashboardInfo(
    @SerializedName("authInfo")
    val authInfo: AuthInfo?,
    @SerializedName("memberInfo")
    val memberInfo: MemberInfo?,
    @SerializedName("total_dues")
    val totalDues: Int?,
    @SerializedName("total_member")
    val totalMember: Int?,
    @SerializedName("total_voucher")
    val totalVoucher: Int?
)