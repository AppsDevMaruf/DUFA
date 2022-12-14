package com.marufalam.dufa.utils

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marufalam.dufa.R


fun Fragment.toast(str: String) {
    Toast.makeText(requireActivity(), str, Toast.LENGTH_SHORT).show()

}

fun Fragment.showAlert(context: Context, msg: String) {

    MaterialAlertDialogBuilder(context)
        .setTitle("Something Went Wrong !")
        .setMessage(msg)
        .setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        .setIcon(R.drawable.ic_round_warning_24)
        .show()
}


fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}


fun Fragment.enableBtn(given: Boolean, btn: Button) {

    // binding.signInBtn.isEnabled = emailGiven && passwordGiven
    btn.isEnabled = given

    if (btn.isEnabled) {
        btn.background =
            AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_cta_btn_back)

        btn.setTextColor(resources.getColor(R.color.text_enable_white))
    } else {
        btn.background =
            AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_rectangle_disable)
        btn.setTextColor(resources.getColor(R.color.text_disable_white))

    }


}




