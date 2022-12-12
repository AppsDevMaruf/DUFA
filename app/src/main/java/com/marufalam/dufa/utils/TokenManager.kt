package com.marufalam.dufa.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var prefs =
        context.getSharedPreferences(Constants.PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(Constants.USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(Constants.USER_TOKEN, null)
    }


    fun saveUserID(id: Int) {
        val editor = prefs.edit()
        editor.putInt(Constants.USER_ID, id)
        editor.apply()
    }

    fun getUserID(): Int? {
        return prefs.getInt(Constants.USER_ID, 0)
    }


}