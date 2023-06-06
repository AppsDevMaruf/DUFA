package org.dufa.dufa9596.data.models.vouchers


import com.google.gson.annotations.SerializedName

data class ResponseVoucherUpload(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
) {
    data class Data(
        @SerializedName("admin_indicate")
        val adminIndicate: Int?,
        @SerializedName("amount")
        val amount: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("date")
        val date: String?,
        @SerializedName("file_name")
        val fileName: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("sender_name")
        val senderName: String?,
        @SerializedName("user_id")
        val userId: Int?,
        @SerializedName("voucher_number")
        val voucherNumber: String?
    )
}