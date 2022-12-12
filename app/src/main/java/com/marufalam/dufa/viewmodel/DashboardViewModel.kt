package com.marufalam.dufa.viewmodel

import android.icu.text.StringSearch
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marufalam.dufa.models.dashboard.ResponseMemberList
import com.marufalam.dufa.models.login.RequestLogin
import com.marufalam.dufa.models.login.ResponseLogin
import com.marufalam.dufa.repos.DashboardRepository
import com.marufalam.dufa.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val dashboardRepo: DashboardRepository) :
    ViewModel() {

    // getMemberList start
    val getMemberListResponse: LiveData<NetworkResult<ResponseMemberList>>
        get() = dashboardRepo.responsegetMemberList

    fun getMemberList() {
        viewModelScope.launch {
            dashboardRepo.getMemberListRepo()
        }
    }
    // getMemberList end


    // getMemberList start


    fun getMemberListSearchVM(search: String, type: String) {
        viewModelScope.launch {
            dashboardRepo.getSearchMemberListRepo(search, type)
        }
    }
    // getMemberList end


}