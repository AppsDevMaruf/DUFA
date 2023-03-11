package com.marufalam.dufa.data.models.dashboard.dasboard_info

data class ResponseMembersDashboardInfo(
    val authInfo: AuthInfo,
    val memberInfo: MemberInfo,
    val total_dues: Int,
    val total_member: Int,
    val total_voucher: Int
)