package com.marufalam.dufa.data.models.getProfileInfo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseProfileInfo(
    @SerializedName("profile")
    val profile: List<Profile?>?
):Parcelable