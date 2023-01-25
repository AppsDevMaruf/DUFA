package com.marufalam.dufa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marufalam.dufa.repos.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val dashboardRepo: DashboardRepository) :
    ViewModel() {

    // get all Member start
    val getAllMemberVMLD = dashboardRepo.responseAllMember
    fun getAllMemberVM() {
        viewModelScope.launch {
            dashboardRepo.getAllMemberRepo()
        }
    }
    // get all Member end

    // getMyProfile start
    val getMyProfileInfoVMLD = dashboardRepo.responseMyProfileRepo

    fun getMyProfileInfoVM() {
        viewModelScope.launch {
            dashboardRepo.getMyProfileRepo()
        }
    }
    // getMyProfile end

    // getMyProfile start
    val getDepartmentsVMLD = dashboardRepo.responseDepartmentsRepo

    fun getDepartmentsVM() {
        viewModelScope.launch {
            dashboardRepo.getDepartmentsRepo()
        }
    }
    // getMyProfile end

/*

    // getMemberList start


    fun getMemberListSearchVM(search: String, type: String) {
        viewModelScope.launch {
            dashboardRepo.getSearchMemberListRepo(search, type)
        }
    }
    // getMemberList end
*/


}