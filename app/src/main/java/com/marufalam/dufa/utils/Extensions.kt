package com.marufalam.dufa.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.View
import android.widget.TextView

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
    return (pxValue / scale *2.5 ).toInt()
}

