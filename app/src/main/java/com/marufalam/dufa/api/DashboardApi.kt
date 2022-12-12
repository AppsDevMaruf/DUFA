package com.marufalam.dufa.api

import com.marufalam.dufa.models.dashboard.ResponseMemberList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DashboardApi {
    @GET("userinfos")
    suspend fun getUserinfos(): Response<ResponseMemberList>


    @POST("search")
    suspend fun getUserByDepartment(@Body department: String): Response<ResponseMemberList>

    @POST("search")
    suspend fun getUserByProfession(@Body profession: String): Response<ResponseMemberList>

    @POST("search")
    suspend fun getUserByBloodGroup(@Body bloodGroup: String): Response<ResponseMemberList>


    @POST("search")
    suspend fun getUserByDistrict(@Body district: String): Response<ResponseMemberList>

}