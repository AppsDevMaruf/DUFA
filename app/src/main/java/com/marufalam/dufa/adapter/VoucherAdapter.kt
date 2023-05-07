package com.marufalam.dufa.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marufalam.dufa.data.models.vouchers.Vouchers


import com.marufalam.dufa.databinding.ItemVoucherBinding
import com.marufalam.dufa.utils.*

class VoucherAdapter : ListAdapter<Vouchers, VoucherAdapter.VoucherViewHolder>(comparator) {

    companion object {

        var comparator = object : DiffUtil.ItemCallback<Vouchers>() {
            override fun areItemsTheSame(oldItem: Vouchers, newItem: Vouchers): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Vouchers, newItem: Vouchers): Boolean {
                return oldItem == newItem
            }
        }


    }


    inner class VoucherViewHolder(var binding: ItemVoucherBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoucherViewHolder {

        return VoucherViewHolder(
            ItemVoucherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VoucherViewHolder, position: Int) {
        getItem(position)?.let { voucher ->
            holder.binding.run {
                /*val voucherImgUrl = Constants.IMG_PREFIX + voucher.fileName
                Log.i("TAG", "voucherImgUrl: $voucherImgUrl ")
                voucherImg.loadImagesWithGlide(voucherImgUrl)*/
                voucherNumber.text = "Number : ${voucher.voucherNumber}"
                voucherAmount.text = "Amount : ${voucher.amount} Tk"
                voucherPaymentDate.text ="Date : ${voucher.date}"
                when (voucher.adminIndicate) {
                    0 -> {
                        val text =
                            "<font color=#2b1473>Status: </font> <font color=#FFB302>Pending</font>"
                        voucherStatus.text =
                            HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    }
                    1 -> {
                        val text =
                            "<font color=#2b1473>Status: </font> <font color=#00AA0E>Approved</font>"
                        voucherStatus.text =
                            HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

                    }
                    2 -> {
                        val text =
                            "<font color=#2b1473>Status: </font> <font color=#FF3838>Rejected</font>"
                        voucherStatus.text =
                            HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

                    }

                }
                voucherDownloadBtn.setOnClickListener {
                    Toast.makeText(holder.itemView.context, "Work in Ongoing..", Toast.LENGTH_LONG).show()

                }
            }

        }
    }


}