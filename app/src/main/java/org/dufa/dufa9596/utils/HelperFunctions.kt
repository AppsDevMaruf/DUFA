package com.fama.famapay.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
fun getFormattedDateTime(value:Long, format:String):String{
    return SimpleDateFormat(format).format(Date(value))
}
