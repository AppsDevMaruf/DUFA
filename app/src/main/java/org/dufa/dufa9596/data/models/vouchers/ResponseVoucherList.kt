package org.dufa.dufa9596.data.models.vouchers


import com.google.gson.annotations.SerializedName

data class ResponseVoucherList(
    @SerializedName("data")
    val vouchers: List<Vouchers?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)