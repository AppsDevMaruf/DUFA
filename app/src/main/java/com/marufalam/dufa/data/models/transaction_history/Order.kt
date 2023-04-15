package com.marufalam.dufa.data.models.transaction_history

data class Order(
    val address: String,
    val amount: Int,
    val created_at: String,
    val currency: String,
    val email: String,
    val id: Int,
    val name: String,
    val payment_method: String,
    val payment_purpose: String,
    val phone: String,
    val status: String,
    val transaction_id: String,
    val updated_at: String
)