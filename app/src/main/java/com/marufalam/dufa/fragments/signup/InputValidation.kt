package com.marufalam.dufa.fragments.signup

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class InputValidation:TextWatcher {
    private var etfeedbackemail: EditText? = null


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }


    override fun afterTextChanged(string: Editable?) {
        // TODO Auto-generated method stub
        if (etfeedbackemail?.getText().toString() == "") {
            etfeedbackemail?.setError("Please enter your email.");
        }
        if (etfeedbackemail?.getText().toString().contains(".*[^a-z^0-9].*")) {
            etfeedbackemail?.setError("Enter a valid address");
        }

    }
}