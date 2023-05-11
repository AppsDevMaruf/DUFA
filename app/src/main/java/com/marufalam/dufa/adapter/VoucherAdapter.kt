package com.marufalam.dufa.adapter


import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
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
import java.net.URLConnection


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
                voucherPaymentDate.text = "Date : ${voucher.date}"
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
                    Toast.makeText(holder.itemView.context, "Downloading...", Toast.LENGTH_LONG)
                        .show()

                    val voucherImgUrl = Constants.IMG_PREFIX + voucher.fileName

                    val uri: Uri = Uri.parse(voucherImgUrl)
                    // Create request for android download manager
                    // Create request for android download manager
                    val downloadManager = holder.itemView.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
                    val request = DownloadManager.Request(uri)
                    request.setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI or
                                DownloadManager.Request.NETWORK_MOBILE
                    )

// set title and description

// set title and description
                    request.setTitle("voucher${System.currentTimeMillis()}.png")
                    request.setDescription("Android Data download using DownloadManager.")

                    request.allowScanningByMediaScanner()
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

//set the local destination for download file to a path within the application's external files directory

//set the local destination for download file to a path within the application's external files directory
                    request.setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        "voucher${System.currentTimeMillis()}"
                    )
                    val mimeType: String = URLConnection.guessContentTypeFromName(voucherImgUrl)
                    println(mimeType)
                    request.setMimeType("application/*")
                    downloadManager!!.enqueue(request)

                }
            }

        }
    }


}