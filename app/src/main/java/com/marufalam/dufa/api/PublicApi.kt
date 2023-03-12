package com.marufalam.dufa.api


import com.marufalam.dufa.data.models.login.RequestLogin
import com.marufalam.dufa.data.models.login.ResponseLogin
import com.marufalam.dufa.data.models.register.RequestRegister
import com.marufalam.dufa.data.models.register.ResponseRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PublicApi {
    @POST("auth/register")
    suspend fun register(@Body requestRegister: RequestRegister): Response<ResponseRegister>

    @POST("auth/login")
    suspend fun login(@Body requestLogin: RequestLogin): Response<ResponseLogin>

   /* @POST("api/logout/")
    fun logOutUser(@Query("token") token: String?): Call<>*/
}