package org.dufa.dufa9596.utils

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Pattern

fun Any.isPasswordMatch(passwordHere: String, confirmHere: String): Boolean {
    return passwordHere == confirmHere
}

fun Any.isLength8(passwordHere: String): Boolean {
    return passwordHere.length > 8
}

fun Any.hasUpperCase(passwordHere: String): Boolean {
    val UpperCasePatten: Pattern = Pattern.compile("[A-Z ]")
    return UpperCasePatten.matcher(passwordHere).find()
}

fun Any.hasLowerCase(passwordHere: String): Boolean {
    val lowerCasePatten: Pattern = Pattern.compile("[a-z ]")
    return lowerCasePatten.matcher(passwordHere).find()
}

fun Any.hasSpecailChar(passwordHere: String): Boolean {
    val specailCharPatten: Pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
    return specailCharPatten.matcher(passwordHere).find()
}

fun Any.hasDigit(passwordHere: String): Boolean {
    val digitCasePatten: Pattern = Pattern.compile("[0-9 ]")
    return digitCasePatten.matcher(passwordHere).find()
}

fun Any.isValidEmail(target: CharSequence?): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}
