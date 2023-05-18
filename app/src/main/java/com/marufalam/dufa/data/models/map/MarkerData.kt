package com.marufalam.dufa.data.models.map

import androidx.annotation.DrawableRes

data class MarkerData(val latitude : Double, val longitude : Double, val title : String, val snippets: String, @DrawableRes val iconResID: Int)