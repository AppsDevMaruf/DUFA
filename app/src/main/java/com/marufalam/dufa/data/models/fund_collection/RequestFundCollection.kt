package com.marufalam.dufa.data.models.fund_collection


import com.google.gson.annotations.SerializedName

data class RequestFundCollection(
    @SerializedName("general_fund")
    val generalFund: Double?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("welfare_fund")
    val welfareFund: Double?,
    @SerializedName("zakat_fund")
    val zakatFund: Double?
)