package org.dufa.dufa9596.data.models.search


import com.google.gson.annotations.SerializedName

data class ResponseSearch(
    @SerializedName("searchData")
    val searchData: SearchData,
    @SerializedName("user")
    val user: User?
)