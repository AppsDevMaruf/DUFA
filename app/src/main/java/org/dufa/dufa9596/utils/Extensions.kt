package org.dufa.dufa9596.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.View
import android.widget.TextView
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

