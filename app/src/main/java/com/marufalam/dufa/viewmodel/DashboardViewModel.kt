package com.marufalam.dufa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.marufalam.dufa.data.models.logout.ResponseLogout
import com.marufalam.dufa.data.models.search.Data
import com.marufalam.dufa.repos.SecuredRepository
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val securedRepository: SecuredRepository) :
    ViewModel() {

    fun getMemberSearchVMLD(
        totalPage: Int,
        searchParam: String,
    ): LiveData<PagingData<Data>> {
        return securedRepository.getMemberSearchRepo(totalPage, searchParam)
            .cachedIn(viewModelScope)
    }

    // get all Member start
    val getAllMemberVMLD = securedRepository.responseAllMember
    fun getAllMemberVM() {
        viewModelScope.launch {
            securedRepository.getAllMemberRepo()
        }
    }
    // get all Member end

    // getMyProfile start
    val getMyProfileInfoVMLD = securedRepository.responseMyProfileRepo

    fun getMyProfileInfoVM() {
        viewModelScope.launch {
            securedRepository.getMyProfileRepo()
        }
    }
    // getMyProfile end

    // getMyProfile start
    val getDepartmentsVMLD = securedRepository.responseDepartmentsRepo

    fun getDepartmentsVM() {
        viewModelScope.launch {
            securedRepository.getDepartmentsRepo()
        }
    }
    // getMyProfile end


    //  logout start

    private var _responseLogout=
        MutableLiveData<NetworkResult<ResponseLogout>>()
    val logoutVMLD: LiveData<NetworkResult<ResponseLogout>>
        get() = _responseLogout

    fun logoutVM() {

        _responseLogout.postValue(NetworkResult.Loading())


        viewModelScope.launch {

            try {
                val response = securedRepository.logout()

                if (response.isSuccessful && response.body() != null) {


                    _responseLogout.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseLogout.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responseLogout.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }

        }

    }


    //   logout  end



}