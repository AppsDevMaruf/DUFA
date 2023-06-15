package org.dufa.dufa9596.api

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import org.dufa.dufa9596.data.local.TokenManager
import org.dufa.dufa9596.utils.Constants
import org.dufa.dufa9596.utils.NoInternetException
import org.dufa.dufa9596.utils.isConnected
import javax.inject.Inject

class AuthInterceptor @Inject constructor(@ApplicationContext var appContext: Context) :
    Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager


    override fun intercept(chain: Interceptor.Chain): Response {
        if (!appContext.isConnected) {
            throw NoInternetException("Make sure You have an active internet connection !")
        } else {
            val request = chain.request().newBuilder()
            val token = tokenManager.getToken(Constants.TOKEN)
            request.addHeader("Authorization", "Bearer $token")
            return chain.proceed(request.build())
        }

    }


}