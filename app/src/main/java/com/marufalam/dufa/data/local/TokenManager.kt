package com.marufalam.dufa.data.local

import android.content.Context
import android.content.SharedPreferences
import com.marufalam.dufa.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var prefs =
        context.getSharedPreferences(Constants.PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(key: String, value: String) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getToken(key: String): String {
        val sharedNameValue = prefs.getString(key, Constants.NO_DATA)
        return sharedNameValue.toString()
    }


    fun setIsLoggedID(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }
    fun getIsLoggedIn(key: String): String {
        val sharedNameValue = prefs.getString(key, Constants.NO_DATA)
        return sharedNameValue.toString()
    }



}