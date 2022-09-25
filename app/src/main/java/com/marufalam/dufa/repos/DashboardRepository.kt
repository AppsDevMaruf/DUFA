package com.marufalam.dufa.repos

import com.marufalam.dufa.models.MemberList
import com.marufalam.weatherapps.networks.NetworkService

class DashboardRepository {
    suspend fun fetchMemberListData():MemberList{
        val endUrl = "userinfos"
        return NetworkService.dufaServiceApi.getMemberListData(endUrl)
    }

}