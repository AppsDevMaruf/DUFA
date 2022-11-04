package com.marufalam.dufa.helper

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.marufalam.dufa.R
import com.marufalam.dufa.networks.getFormattedDate


@BindingAdapter("app:setDateTime")
fun setDateTime(tv:TextView, dt:Long){
    tv.text= getFormattedDate(dt,"EEE h:mm a")

}
@BindingAdapter("app:setIcon")
fun setIcon(img:ImageView,imgUrl:String?){
if (imgUrl != null){
    Glide.with(img.context)
        .load(imgUrl)
        .placeholder(R.drawable.loadpreview)
        .into(img)
}else{
    Glide.with(img.context)
        .load("")
        .placeholder(R.drawable.loadpreview)
        .into(img)
}




}
