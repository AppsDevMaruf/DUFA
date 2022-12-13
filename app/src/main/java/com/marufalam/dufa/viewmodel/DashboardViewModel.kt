package com.marufalam.dufa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marufalam.dufa.data.models.dashboard.ResponseMemberList
import com.marufalam.dufa.data.models.getProfileInfo.ResponseProfileInfo
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

    // getMyProfile start
    val getMyProfileInfoVMLD: LiveData<NetworkResult<ResponseProfileInfo>>
        get() = dashboardRepo.responseMyProfileRepo

    fun getMyProfileInfoVM() {
        viewModelScope.launch {
            dashboardRepo.getMyProfileRepo()
        }
    }
    // getMyProfile end


    // getMemberList start


    fun getMemberListSearchVM(search: String, type: String) {
        viewModelScope.launch {
            dashboardRepo.getSearchMemberListRepo(search, type)
        }
    }
    // getMemberList end


}