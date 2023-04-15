package com.marufalam.dufa.data.models.get_departments


import com.google.gson.annotations.SerializedName

data class ResponseDepartments(
    @SerializedName("departments")
    val departments: List<Department?>?
)