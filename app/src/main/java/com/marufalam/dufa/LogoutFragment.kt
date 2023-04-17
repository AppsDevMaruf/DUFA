package com.marufalam.dufa

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.utils.Constants
import com.marufalam.dufa.utils.toast


class LogoutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val prefs = context?.getSharedPreferences(Constants.PREFS_TOKEN_FILE, Context.MODE_PRIVATE)
        val editor = prefs?.edit()
        editor?.putString(Constants.USER_TOKEN,null)
        editor?.clear()
        editor?.apply()
        //navigation

        return inflater.inflate(R.layout.fragment_logout, container, false)
    }

}