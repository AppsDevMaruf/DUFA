package com.marufalam.dufa.helper

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.marufalam.dufa.R
import com.marufalam.dufa.utils.Constants

@BindingAdapter("app:setIcon")
fun setIcon(img:ImageView,imgUrl:String?){
    val url = Constants.IMG_PREFIX+imgUrl
    Glide.with(img.context)
        .load(url)
        .placeholder(R.drawable.loadpreview)
        .into(img)




}
