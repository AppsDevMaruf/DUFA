package com.marufalam.dufa.utils

import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import java.util.regex.Pattern

fun Fragment.isPasswordMatch(passwordHere: String, confirmHere: String): Boolean {
    return passwordHere == confirmHere
}

fun Fragment.isLength8(passwordHere: String): Boolean {
    return passwordHere.length > 8
}

fun Fragment.hasUpperCase(passwordHere: String): Boolean {
    val UpperCasePatten: Pattern = Pattern.compile("[A-Z ]")
    return UpperCasePatten.matcher(passwordHere).find()
}

fun Fragment.hasLowerCase(passwordHere: String): Boolean {
    val lowerCasePatten: Pattern = Pattern.compile("[a-z ]")
    return lowerCasePatten.matcher(passwordHere).find()
}

fun Fragment.hasSpecailChar(passwordHere: String): Boolean {
    val specailCharPatten: Pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
    return specailCharPatten.matcher(passwordHere).find()
}

fun Fragment.hasDigit(passwordHere: String): Boolean {
    val digitCasePatten: Pattern = Pattern.compile("[0-9 ]")
    return digitCasePatten.matcher(passwordHere).find()
}

fun Fragment.isValidEmail(target: CharSequence?): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}
