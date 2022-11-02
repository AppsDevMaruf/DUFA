package com.marufalam.dufa.repos

import com.marufalam.dufa.models.MemberList
import com.marufalam.dufa.models.login.RequestLogin
import com.marufalam.dufa.models.login.ResponseLogin
import com.marufalam.dufa.networks.NetworkService

class DashboardRepository {
    suspend fun fetchMemberListData(): MemberList {
        val endUrl = "userinfos"
        return NetworkService.dufaServiceApi.getMemberListData(endUrl)
    }

    suspend fun loginUser(userRequest: RequestLogin): ResponseLogin {
        return NetworkService.dufaServiceApi.login(userRequest).body()!!
    }


}