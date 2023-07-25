package org.dufa.dufa9596.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import org.dufa.dufa9596.api.AuthInterceptor
import org.dufa.dufa9596.api.PublicApi
import org.dufa.dufa9596.api.SecuredApi
import org.dufa.dufa9596.utils.Constants
import org.dufa.dufa9596.utils.MySSLSocketFactory.createSSLSocketFactory
import org.dufa.dufa9596.utils.MyTrustManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit.Builder {

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    }

    // Create SSL socket factory
    private val sslSocketFactory = createSSLSocketFactory()

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor)
           .sslSocketFactory(sslSocketFactory, MyTrustManager())
            .hostnameVerifier { _, _ -> true } // Accept all hostnames
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesUserApi(retrofitBuilder: Retrofit.Builder): PublicApi {
        return retrofitBuilder.build().create(PublicApi::class.java)
    }

    @Provides
    @Singleton
    fun providesDashboardApi(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): SecuredApi {
        return retrofitBuilder.client(okHttpClient).build().create(SecuredApi::class.java)
    }

}