package com.marufalam.dufa.api

import com.marufalam.dufa.data.local.TokenManager
import com.marufalam.dufa.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        val token = tokenManager.getToken(Constants.TOKEN)
        request.addHeader("token", token)
        return chain.proceed(request.build())
    }


}