package org.dufa.dufa9596.repos

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.dufa.dufa9596.api.PublicApi
import org.dufa.dufa9596.data.models.locations.RequestSetCLocation
import org.dufa.dufa9596.data.models.login.RequestLogin
import org.dufa.dufa9596.data.models.login.ResponseLogin
import org.dufa.dufa9596.data.models.register.RequestRegister
import org.dufa.dufa9596.data.models.register.ResponseRegister
import org.dufa.dufa9596.data.models.vouchers.ResponseVoucherList
import org.dufa.dufa9596.utils.NetworkResult
import org.dufa.dufa9596.utils.NoInternetException
import org.json.JSONObject
import javax.inject.Inject

class PublicRepository @Inject constructor(private val userApi: PublicApi) {

    private val _registerResponseLiveDataRepo = MutableLiveData<NetworkResult<ResponseRegister>>()
    val registerResponseLiveDataRepo: LiveData<NetworkResult<ResponseRegister>>
        get() = _registerResponseLiveDataRepo

    suspend fun registerUserRepo(requestRegister: RequestRegister) {
        _registerResponseLiveDataRepo.postValue(NetworkResult.Loading())
        val response = userApi.register(requestRegister)
        if (response.isSuccessful && response.body() != null) {
            _registerResponseLiveDataRepo.postValue(NetworkResult.Success(response.body()!!))

        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _registerResponseLiveDataRepo.postValue(NetworkResult.Error(errorObj.getString("message")))


        } else {
            _registerResponseLiveDataRepo.postValue(NetworkResult.Error(".Something Wrong....."))
        }

    }

    suspend fun loginRepo(requestLogin: RequestLogin) = userApi.login(requestLogin)



}