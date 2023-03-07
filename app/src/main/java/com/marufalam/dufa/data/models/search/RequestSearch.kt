package com.marufalam.dufa.data.models.search


import com.google.gson.annotations.SerializedName

data class RequestSearch(
    @SerializedName("birthdate")
    val birthdate: String?,
    @SerializedName("bloodgroup")
    val bloodgroup: String?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("district")
    val district: String?,
    @SerializedName("occupation")
    val occupation: String?,
    @SerializedName("page")
    val page: Int?
)