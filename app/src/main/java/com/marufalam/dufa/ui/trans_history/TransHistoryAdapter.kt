package com.marufalam.dufa.ui.trans_history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marufalam.dufa.data.models.transaction_history.Order
import com.marufalam.dufa.data.models.transaction_history.TransHistory
import com.marufalam.dufa.databinding.ItemTransHistoryBinding
import com.marufalam.dufa.utils.getZonedTime
import com.marufalam.dufa.utils.removeUnderscore
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class TransHistoryAdapter : ListAdapter<Order, TransHistoryAdapter.TransViewHolder>(comparator) {


    companion object {

        var comparator = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem == newItem
            }
        }


    }


    inner class TransViewHolder(var binding: ItemTransHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransViewHolder {

        return TransViewHolder(
            ItemTransHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TransViewHolder, position: Int) {
        getItem(position)?.let { order ->
            holder.binding.run {
                name.text = order.name
                email.text = order.email
                mobile.text = order.phone
                amount.text = "Tk. ${order.amount}"
                paymentDate.text = "PaymentDate: ${getZonedTime(order.created_at.toString())}"
                status.text = order.status
                paymentMethod.text = order.payment_method
                paymentPurpose.text = order.payment_purpose?.let { removeUnderscore(it) }
            }


        }
    }
}