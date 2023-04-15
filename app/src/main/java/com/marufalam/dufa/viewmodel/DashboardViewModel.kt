package com.marufalam.dufa.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.marufalam.dufa.data.models.dashboard.dasboard_info.ResponseDashboardInfo
import com.marufalam.dufa.data.models.getProfileInfo.ResponseProfileInfo
import com.marufalam.dufa.data.models.get_districts.ResponseDistrict
import com.marufalam.dufa.data.models.get_halls.ResponseHalls
import com.marufalam.dufa.data.models.get_occupations.ResponseOccupations
import com.marufalam.dufa.data.models.logout.ResponseLogout
import com.marufalam.dufa.data.models.search.Data
import com.marufalam.dufa.data.models.search.RequestSearch
import com.marufalam.dufa.data.models.upload_profile_pic.ResponseUploadProfilePic
import com.marufalam.dufa.repos.SecuredRepository
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
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

  /*  //  searchByNameEmail start

    private var _responeSearchByNameEmail =
        MutableLiveData<NetworkResult<ResponseSearch>>()
    val searchByNameEmailVMLD: LiveData<NetworkResult<ResponseSearch>>
        get() = _responeSearchByNameEmail

    fun searchByNameEmailVM(nameOrEmail:RequestSearch) {

        _responeSearchByNameEmail.postValue(NetworkResult.Loading())

        viewModelScope.launch {

            try {
                val response = securedRepository.searchByNameEmail(nameOrEmail)

                if (response.isSuccessful && response.body() != null) {


                    _responeSearchByNameEmail.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responeSearchByNameEmail.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responeSearchByNameEmail.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }

        }

    }


    //   searchByNameEmail info  end
*/
    //  District info start

    private var _responseDistrict =
        MutableLiveData<NetworkResult<ResponseDistrict>>()
    val districtVMLD: LiveData<NetworkResult<ResponseDistrict>>
        get() = _responseDistrict

    fun districtVM() {

        _responseDistrict.postValue(NetworkResult.Loading())

        viewModelScope.launch {

            try {
                val response = securedRepository.getDistricts()

                if (response.isSuccessful && response.body() != null) {


                    _responseDistrict.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseDistrict.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responseDistrict.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }

        }

    }


    //   District info  end


    //  District info start

    private var _responseOccupations =
        MutableLiveData<NetworkResult<ResponseOccupations>>()
    val occupationsVMLD: LiveData<NetworkResult<ResponseOccupations>>
        get() = _responseOccupations

    fun occupationsVM() {

        _responseOccupations.postValue(NetworkResult.Loading())

        viewModelScope.launch {

            try {
                val response = securedRepository.getOccupations()

                if (response.isSuccessful && response.body() != null) {


                    _responseOccupations.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseOccupations.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responseOccupations.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }

        }

    }


    //   District info  end


    //  Halls info start

    private var _responseHalls =
        MutableLiveData<NetworkResult<ResponseHalls>>()
    val hallsVMLD: LiveData<NetworkResult<ResponseHalls>>
        get() = _responseHalls

    fun hallsVM() {

        _responseHalls.postValue(NetworkResult.Loading())

        viewModelScope.launch {

            try {
                val response = securedRepository.getHalls()

                if (response.isSuccessful && response.body() != null) {


                    _responseHalls.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseHalls.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responseHalls.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }

        }

    }


    //   District info  end


    //  dashboard info start

    private var _responseDashboardInfo =
        MutableLiveData<NetworkResult<ResponseDashboardInfo>>()
    val dashboardInfoVMLD: LiveData<NetworkResult<ResponseDashboardInfo>>
        get() = _responseDashboardInfo

    fun dashboardInfoVM() {

        _responseDashboardInfo.postValue(NetworkResult.Loading())


        viewModelScope.launch {

            try {
                val response = securedRepository.getDashboardInfo()
                Log.i("TAG", "dashboardInfoVM: $response")
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
        MutableLiveData<NetworkResult<ResponseUploadProfilePic>>()
    val uploadProfilePicVMLD: LiveData<NetworkResult<ResponseUploadProfilePic>>
        get() = _responseUploadProfilePic

   suspend fun uploadProfilePicVM(userId:Int,part:MultipartBody.Part) {

        _responseUploadProfilePic.postValue(NetworkResult.Loading())


        viewModelScope.launch {

            try {
                val response = securedRepository.uploadProfilePic(userId,part)

                Log.i("TAG", "uploadProfilePicVM: $response")

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