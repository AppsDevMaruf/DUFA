package com.marufalam.dufa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.marufalam.dufa.data.models.dashboard.dasboard_info.ResponseMembersDashboardInfo
import com.marufalam.dufa.data.models.getProfileInfo.ResponseProfileInfo
import com.marufalam.dufa.data.models.logout.ResponseLogout
import com.marufalam.dufa.data.models.search.Data
import com.marufalam.dufa.data.models.search.RequestSearch
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

        requestSearch: RequestSearch

    ): LiveData<PagingData<Data>> {
        return securedRepository.getMemberSearchRepo(requestSearch)
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

   /* // getMyProfile start
    val getMyProfileInfoVMLD = securedRepository.responseMyProfileRepo

    fun getMyProfileInfoVM() {
        viewModelScope.launch {
            securedRepository.getMyProfileRepo()
        }
    }
    // getMyProfile end*/


    // getDepartmentsVM start
    val getDepartmentsVMLD = securedRepository.responseDepartmentsRepo

    fun getDepartmentsVM() {
        viewModelScope.launch {
            securedRepository.getDepartmentsRepo()
        }
    }
    // getDepartmentsVM end

    // getBloodGroupVM start
    val getBloodGroupVMLD = securedRepository.responseBloodGroupRepo

    fun getBloodGroupVM() {
        viewModelScope.launch {
            securedRepository.getBloodGroupsRepo()
        }
    }
    // getBloodGroupVM end


    //  logout start

    private var _responseLogout =
        MutableLiveData<NetworkResult<ResponseLogout>>()
    val logoutVMLD: LiveData<NetworkResult<ResponseLogout>>
        get() = _responseLogout

   suspend fun logoutVM() {

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

    //  profile info start

    private var _responseProfileInfo =
        MutableLiveData<NetworkResult<ResponseProfileInfo>>()
    val profileInfoVMLD: LiveData<NetworkResult<ResponseProfileInfo>>
        get() = _responseProfileInfo

    fun profileInfoVM() {

        _responseProfileInfo.postValue(NetworkResult.Loading())

        viewModelScope.launch {

            try {
                val response = securedRepository.getProfile()

                if (response.isSuccessful && response.body() != null) {


                    _responseProfileInfo.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseProfileInfo.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responseProfileInfo.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }

        }

    }


    //   profile info  end

    //  dashboard info start

    private var _responseDashboardInfo =
        MutableLiveData<NetworkResult<ResponseMembersDashboardInfo>>()
    val dashboardInfoVMLD: LiveData<NetworkResult<ResponseMembersDashboardInfo>>
        get() = _responseDashboardInfo

    fun dashboardInfoVM() {

        _responseDashboardInfo.postValue(NetworkResult.Loading())


        viewModelScope.launch {

            try {
                val response = securedRepository.getDashboardInfo()

                if (response.isSuccessful && response.body() != null) {


                    _responseDashboardInfo.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseDashboardInfo.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responseDashboardInfo.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }

        }

    }


    //   dashboard info  end

    //  upload profile pic start

    private var _responseUploadProfilePic =
        MutableLiveData<NetworkResult<ResponseMembersDashboardInfo>>()
    val uploadProfilePicVMLD: LiveData<NetworkResult<ResponseMembersDashboardInfo>>
        get() = _responseUploadProfilePic

   suspend fun uploadProfilePicVM() {

        _responseUploadProfilePic.postValue(NetworkResult.Loading())


        viewModelScope.launch {

            try {
                val response = securedRepository.getDashboardInfo()

                if (response.isSuccessful && response.body() != null) {


                    _responseUploadProfilePic.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseUploadProfilePic.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responseUploadProfilePic.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }

        }

    }


    //   upload profile pic  end

}