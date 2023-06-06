package org.dufa.dufa9596.api

import org.dufa.dufa9596.data.models.login.RequestLogin
import org.dufa.dufa9596.data.models.login.ResponseLogin
import org.dufa.dufa9596.data.models.register.RequestRegister
import org.dufa.dufa9596.data.models.register.ResponseRegister
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