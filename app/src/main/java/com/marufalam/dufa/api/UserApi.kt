package com.marufalam.dufa.api


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/users/signup")
    suspend fun signup(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("/users/signin")
    suspend fun signin(@Body userRequest: UserRequest): Response<UserResponse>
}