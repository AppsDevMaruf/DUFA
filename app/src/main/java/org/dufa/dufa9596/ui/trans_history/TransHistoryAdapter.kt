package org.dufa.dufa9596.ui.trans_history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.dufa.dufa9596.data.models.transaction_history.Order
import org.dufa.dufa9596.databinding.ItemTransHistoryBinding
import org.dufa.dufa9596.utils.getZonedTime
import org.dufa.dufa9596.utils.removeUnderscore
import org.dufa.dufa9596.utils.setTextNonNull
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
                order.created_at?.let {
                    paymentDate.text = "PaymentDate: ${getZonedTime(it)}"
                }
                amount.setTextNonNull("Tk. ${order.amount} Tk")
                status.text = order.status
                paymentMethod.text = order.payment_method
                paymentPurpose.text = order.payment_purpose?.let { removeUnderscore(it) }
            }

        }
    }
}