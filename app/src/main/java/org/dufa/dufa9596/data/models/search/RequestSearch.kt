package org.dufa.dufa9596.data.models.search
import com.google.gson.annotations.SerializedName

data class RequestSearch(
    @SerializedName("birth_date")
    val birth_date: String?,
    @SerializedName("bloodgroup")
    val bloodgroup: String?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("district")
    val district: String?,
    @SerializedName("occupation")
    val occupation: String?,
    @SerializedName("search")
    val search: String?,
    @SerializedName("hall")
    val hall: String?,
    @SerializedName("page")
    val page: Int?
)