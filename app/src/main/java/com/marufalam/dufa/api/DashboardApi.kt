package com.marufalam.dufa.api

import com.marufalam.dufa.models.dashboard.ResponseMemberList
import retrofit2.Response
import retrofit2.http.GET

interface DashboardApi {
    @GET("userinfos")
    suspend fun getUserinfos(): Response<ResponseMemberList>
}