package org.dufa.dufa9596.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import org.dufa.dufa9596.R
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

fun Fragment.datePickerFun(textView: TextView) {
    val mCurrentDate: Calendar = Calendar.getInstance()
    val mYear: Int = mCurrentDate.get(Calendar.YEAR)
    val mMonth: Int = mCurrentDate.get(Calendar.MONTH)
    val mDay: Int = mCurrentDate.get(Calendar.DAY_OF_MONTH)

    val mDatePicker = DatePickerDialog(
        requireActivity(), R.style.DatePickerTheme,
        { _, selectedYear, selectedMonth, selectedDay ->


            val mFormat = DecimalFormat("00")
            mFormat.roundingMode = RoundingMode.DOWN
            val date: String =
                mFormat.format((selectedYear)) + "-" + mFormat.format(
                    (selectedMonth + 1)
                ) + "-" + mFormat.format((selectedDay))

            textView.text = date

        }, mYear , mMonth, mDay
    )

    mDatePicker.show()


}


fun dpFromPx(context: Context, px: Float): Float {
    return px / context.resources.displayMetrics.density
}

fun pxFromDp(context: Context, dp: Float): Float {
    return (dp * context.resources.displayMetrics.density) / 3
}

fun TextView.fontSize(context: Context, px: Float) {
    val fontsize = px2dip(context, px).toFloat()
    textSize = fontsize

}


//SYSTEM UI ICON


fun Activity.iconColor(activity: Activity) {


    if (Build.VERSION.SDK_INT >= 23) {
        val decor = activity.window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }


}


fun pxToDp(textView: TextView, pxs: String) {
    val px: Int = pxs.toInt()

    val size = (px / Resources.getSystem().displayMetrics.density) as Float
    textView.textSize = size

}

fun TextView.px2dip(context: Context, pxValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (pxValue / scale * 2.5).toInt()
}
val Context.isConnected: Boolean
    get() {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                val nw = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
            else -> {
                // Use depreciated methods only on older devices
                val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                nwInfo.isConnected
            }
        }
    }
fun Fragment.showDialog(
    context: Context,

    title: String,
    details: String,
    resId: Int,
    yesContent: String,
    noContent: String,
    showNoBtn: Boolean,
    positiveFun: () -> Unit,
    negativeFun: () -> Unit,

    ) {

    val deleteDialogView: View = LayoutInflater.from(context)
        .inflate(R.layout.item_dialog, null)
    val deleteDialog: AlertDialog = AlertDialog.Builder(context).setCancelable(false).create()
    deleteDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    deleteDialog.setView(deleteDialogView)

    val titleTv =
        deleteDialogView.findViewById<TextView>(R.id.titleTv)

    val detailsTv =
        deleteDialogView.findViewById<TextView>(R.id.detailsTv)
    val yesButton =
        deleteDialogView.findViewById<Button>(R.id.yesBtn)
    val noButton =
        deleteDialogView.findViewById<Button>(R.id.noBtn)
    val logoIcon =
        deleteDialogView.findViewById<ImageView>(R.id.topIcon)
    titleTv.text = title
    detailsTv.text = details
    yesButton.text = yesContent

    if (showNoBtn) {
        noButton.show()
        noButton.text = noContent
    } else {
        noButton.gone()
    }


    logoIcon.setImageResource(resId)


    yesButton.setOnClickListener {
        positiveFun.invoke()
        deleteDialog.dismiss()
    }
    noButton.setOnClickListener {
        negativeFun.invoke()
        deleteDialog.dismiss()
    }



    deleteDialog.show()


}
fun Activity.showDialog(
    context: Context,

    title: String,
    details: String,
    resId: Int,
    yesContent: String,
    noContent: String,
    showNoBtn: Boolean,
    positiveFun: () -> Unit,
    negativeFun: () -> Unit,

    ) {

    val deleteDialogView: View = LayoutInflater.from(context)
        .inflate(R.layout.item_dialog, null)
    val deleteDialog: AlertDialog = AlertDialog.Builder(context).setCancelable(false).create()
    deleteDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    deleteDialog.setView(deleteDialogView)

    val titleTv =
        deleteDialogView.findViewById<TextView>(R.id.titleTv)

    val detailsTv =
        deleteDialogView.findViewById<TextView>(R.id.detailsTv)
    val yesButton =
        deleteDialogView.findViewById<Button>(R.id.yesBtn)
    val noButton =
        deleteDialogView.findViewById<Button>(R.id.noBtn)
    val logoIcon =
        deleteDialogView.findViewById<ImageView>(R.id.topIcon)
    titleTv.text = title
    detailsTv.text = details
    yesButton.text = yesContent

    if (showNoBtn) {
        noButton.show()
        noButton.text = noContent
    } else {
        noButton.gone()
    }


    logoIcon.setImageResource(resId)


    yesButton.setOnClickListener {
        positiveFun.invoke()
        deleteDialog.dismiss()
    }
    noButton.setOnClickListener {
        negativeFun.invoke()
        deleteDialog.dismiss()
    }



    deleteDialog.show()


}

