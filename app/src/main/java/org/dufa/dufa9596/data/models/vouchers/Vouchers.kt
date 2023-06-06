package org.dufa.dufa9596.data.models.vouchers


import com.google.gson.annotations.SerializedName

data class Vouchers(
    @SerializedName("admin_indicate")
    val adminIndicate: Int?,
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("approval_name")
    val approvalName: String?,
    @SerializedName("comments")
    val comments: String?,
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
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("voucher_number")
    val voucherNumber: String?
)