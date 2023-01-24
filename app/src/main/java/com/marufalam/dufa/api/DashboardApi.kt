package com.marufalam.dufa.api

import com.marufalam.dufa.data.models.dashboard.ResponseAllMember
import com.marufalam.dufa.data.models.getProfileInfo.ResponseProfileInfo
import retrofit2.Response
import retrofit2.http.GET

interface DashboardApi {
    @GET("get-all-members")
    suspend fun getAllMember(): Response<ResponseAllMember>

    @GET("get-userinfo-profile")
    suspend fun getProfileInfo(): Response<ResponseProfileInfo>

   /* @POST("search")
    suspend fun getUserByDepartment(@Body department: String): Response<ResponseMemberList>

    @POST("search")
    suspend fun getUserByProfession(@Body profession: String): Response<ResponseMemberList>

    @POST("search")
    suspend fun getUserByBloodGroup(@Body bloodGroup: String): Response<ResponseMemberList>


    @POST("search")
    suspend fun getUserByDistrict(@Body district: String): Response<ResponseMemberList>*/

}