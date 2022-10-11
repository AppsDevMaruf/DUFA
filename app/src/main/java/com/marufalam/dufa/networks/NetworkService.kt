package com.marufalam.weatherapps.networks

import android.annotation.SuppressLint
import com.marufalam.dufa.models.MemberList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.text.SimpleDateFormat
import java.util.*


const val baseUrl = "https://dufa9596.org/api/"


@SuppressLint("SimpleDateFormat")
fun getFormattedDate(dt:Long, pattern: String) =
     SimpleDateFormat(pattern).format(Date(dt*1000))



val retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface DufaServiceApi{
    @GET
   suspend fun getMemberListData(@Url endUrl:String):MemberList

}

object NetworkService{
    val dufaServiceApi: DufaServiceApi by lazy {
        retrofit.create(DufaServiceApi::class.java)
    }
}