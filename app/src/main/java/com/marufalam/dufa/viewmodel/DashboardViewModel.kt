package com.marufalam.dufa.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marufalam.dufa.models.MemberList
import com.marufalam.dufa.models.login.RequestLogin
import com.marufalam.dufa.models.login.ResponseLogin
import com.marufalam.dufa.repos.DashboardRepository
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    val repository = DashboardRepository()
    val memberlistLD: MutableLiveData<MemberList> = MutableLiveData()
    val userLoginLiveData: MutableLiveData<ResponseLogin> = MutableLiveData()

    fun fetchData() {
        viewModelScope.launch {
            try {
                memberlistLD.value = repository.fetchMemberListData()
                Log.e("TAG", "fetchData: $memberlistLD")
            } catch (e: Exception) {
                Log.e("DashboardViewModel", e.localizedMessage)
            }
        }

    }


    fun login(userRequest: RequestLogin) {

        viewModelScope.launch {
            try {

                userLoginLiveData.value = repository.loginUser(userRequest)
            } catch (e: Exception) {


            }


        }


    }


}