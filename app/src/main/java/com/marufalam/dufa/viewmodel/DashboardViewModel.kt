package com.marufalam.dufa.viewmodel

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import com.marufalam.dufa.data.models.payRenew.RequestPayRenew
import com.marufalam.dufa.data.models.search.Data
import com.marufalam.dufa.data.models.search.RequestSearch
import com.marufalam.dufa.data.models.upload_profile_pic.ResponseUploadProfilePic
import com.marufalam.dufa.data.models.vouchers.RequestVoucher
import com.marufalam.dufa.data.models.vouchers.ResponseVoucherUpload
import com.marufalam.dufa.repos.SecuredRepository
import com.marufalam.dufa.ui.profile_update.RequestProfileUpdate
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val securedRepository: SecuredRepository) :
    ViewModel() {


    var _paymentUrl = MutableLiveData<String>()
    val paymentUrl: LiveData<String> = _paymentUrl


    fun savePaymentUrl(url: String) {

        _paymentUrl.postValue(url)


    }

    var _paymentDues = MutableLiveData<Int>()
    val paymentDues: LiveData<Int> = _paymentDues


    fun savePaymentDues(dues: Int) {

        _paymentDues.postValue(dues)


    }


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

    @SuppressLint("SuspiciousIndentation")
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

    suspend fun uploadProfilePicVM(userId: Int, part: MultipartBody.Part) {

        _responseUploadProfilePic.postValue(NetworkResult.Loading())


        viewModelScope.launch {

            try {
                val response = securedRepository.uploadProfilePic(userId, part)

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

    //  upload profile pic start

    private var _responseUploadVoucher =
        MutableLiveData<NetworkResult<ResponseVoucherUpload>>()
    val responseUploadVoucherVMLD: LiveData<NetworkResult<ResponseVoucherUpload>>
        get() = _responseUploadVoucher

    suspend fun uploadVoucherVm(
        date: RequestBody,
        amount: RequestBody,
        voucher_number: RequestBody,
        part: MultipartBody.Part
    ) {

        _responseUploadVoucher.postValue(NetworkResult.Loading())


        viewModelScope.launch {

            try {
                val response = securedRepository.uploadVoucher(
                    part = part,
                    date = date,
                    amount = amount,
                    voucher_number = voucher_number
                )

                Log.i("TAG", "uploadProfilePicVM: $response")

                if (response.isSuccessful && response.body() != null) {


                    _responseUploadVoucher.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseUploadVoucher.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: Exception) {
                _responseUploadVoucher.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }

        }

    }

    //   upload profile pic  end


    //  upload profile pic start

    private var _responseUpdateProfile =
        MutableLiveData<NetworkResult<ResponseUploadProfilePic>>()
    val responseUpdateProfileVMLD: LiveData<NetworkResult<ResponseUploadProfilePic>>
        get() = _responseUpdateProfile

    suspend fun updateProfileVM(userId: Int, requestProfileUpdate: RequestProfileUpdate) {

        _responseUpdateProfile.postValue(NetworkResult.Loading())


        viewModelScope.launch {

            try {
                val response = securedRepository.updateProfile(userId, requestProfileUpdate)

                Log.i("TAG", "uploadProfilePicVM: $response")

                if (response.isSuccessful && response.body() != null) {


                    _responseUpdateProfile.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseUpdateProfile.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: Exception) {
                _responseUpdateProfile.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }

        }

    }

    //   upload profile pic  end


    //  payRenew  start

    private var _responsePayRenew =
        MutableLiveData<NetworkResult<String>>()
    val responsePayRenewVMLD: LiveData<NetworkResult<String>>
        get() = _responsePayRenew

    fun payRenewVM(requestPayRenew: RequestPayRenew) {

        _responsePayRenew.postValue(NetworkResult.Loading())
        val response = securedRepository.payRenew(requestPayRenew)

        response.enqueue(object : Callback<ResponseBody> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<ResponseBody>, response: Response<ResponseBody>
            ) {
                try {
                    if (response.isSuccessful && response.body() != null) {
                        _responsePayRenew.postValue(
                            NetworkResult.Success(
                                response.body()?.string()!!
                            )
                        )
                    } else {
                        val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                        _responsePayRenew.postValue(NetworkResult.Error(errorObj.getString("message")))
                    }
                } catch (e: Exception) {

                    _responsePayRenew.postValue(e.localizedMessage?.let {
                        NetworkResult.Error(
                            it
                        )
                    })

                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("TAG", "onFailure: ${t.message} ")
            }
        })
    }
    //   payRenew  end
}