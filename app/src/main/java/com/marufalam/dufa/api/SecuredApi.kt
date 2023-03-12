package com.marufalam.dufa.api

import com.marufalam.dufa.data.models.dashboard.ResponseAllMember
import com.marufalam.dufa.data.models.dashboard.dasboard_info.ResponseMembersDashboardInfo
import com.marufalam.dufa.data.models.getProfileInfo.ResponseProfileInfo
import com.marufalam.dufa.data.models.get_departments.ResponseDepartments
import com.marufalam.dufa.data.models.logout.ResponseLogout
import com.marufalam.dufa.data.models.search.RequestSearch
import com.marufalam.dufa.data.models.search.ResponseSearch
import com.marufalam.dufa.data.models.search.blood.ResponseBloodGroup
import com.marufalam.dufa.data.models.upload_profile_pic.ResponseUploadProfilePic
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface SecuredApi {
    @GET("get-all-members")
    suspend fun getAllMember(): Response<ResponseAllMember>

    @GET("get_departments")
    suspend fun getDepartments(): Response<ResponseDepartments>

    @GET("get_bloodgroups")
    suspend fun getBloodGroups(): Response<ResponseBloodGroup>

    @GET("get-userinfo-profile")
    //@Header("AUTHORIZATION") value: String
    suspend fun getProfileInfo(): Response<ResponseProfileInfo>

    @POST("search")
    //@Header("AUTHORIZATION") value: String
    suspend fun getSearchResult(
        @Body page: RequestSearch
    ): ResponseSearch

    @POST("logout")
    suspend fun logout(): Response<ResponseLogout>

    @POST("members_dashboard")
    suspend fun getDashboardInfo(): Response<ResponseMembersDashboardInfo>

    @Multipart
    @PUT("member-profile-update/{userID}")
    suspend fun uploadProfilePic(
        @Path("userID") userID: Int,
        @Part image: MultipartBody.Part,
    ): Response<ResponseUploadProfilePic>


    /* @POST("search")
     suspend fun getUserByDepartment(@Body department: String): Response<ResponseMemberList>

     @POST("search")
     suspend fun getUserByProfession(@Body profession: String): Response<ResponseMemberList>

     @POST("search")
     suspend fun getUserByBloodGroup(@Body bloodGroup: String): Response<ResponseMemberList>


     @POST("search")
     suspend fun getUserByDistrict(@Body district: String): Response<ResponseMemberList>*/

}