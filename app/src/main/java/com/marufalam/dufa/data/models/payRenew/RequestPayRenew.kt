package com.marufalam.dufa.data.models.payRenew
import com.google.gson.annotations.SerializedName

data class RequestPayRenew(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("membership")
    val membership: String?,
    @SerializedName("userinfo_id")
    val userinfoID: Int?,
    @SerializedName("renew_fee")
    val renewFee: String?
)