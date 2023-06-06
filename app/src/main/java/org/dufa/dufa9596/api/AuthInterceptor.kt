package org.dufa.dufa9596.api

import okhttp3.Interceptor
import okhttp3.Response
import org.dufa.dufa9596.data.local.TokenManager
import org.dufa.dufa9596.utils.Constants
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = tokenManager.getToken(Constants.TOKEN)
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }


}