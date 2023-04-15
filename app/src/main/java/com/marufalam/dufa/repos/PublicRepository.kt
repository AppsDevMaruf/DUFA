package com.marufalam.dufa.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marufalam.dufa.api.PublicApi
import com.marufalam.dufa.data.models.login.RequestLogin
import com.marufalam.dufa.data.models.login.ResponseLogin
import com.marufalam.dufa.data.models.register.RequestRegister
import com.marufalam.dufa.data.models.register.ResponseRegister
import com.marufalam.dufa.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class PublicRepository @Inject constructor(private val userApi: PublicApi) {

    private val _registerResponseLiveDataRepo = MutableLiveData<NetworkResult<ResponseRegister>>()
    val registerResponseLiveDataRepo: LiveData<NetworkResult<ResponseRegister>>
        get() = _registerResponseLiveDataRepo

    private val _loginResponseLiveDataRepo = MutableLiveData<NetworkResult<ResponseLogin>>()
    val loginResponseLiveDataRepo: LiveData<NetworkResult<ResponseLogin>>
        get() = _loginResponseLiveDataRepo

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

    suspend fun loginUserRepo(requestLogin: RequestLogin) {
        _loginResponseLiveDataRepo.postValue(NetworkResult.Loading())
        try {
            val response = userApi.login(requestLogin)
            if (response.isSuccessful && response.body() != null) {
                _loginResponseLiveDataRepo.postValue(NetworkResult.Success(response.body()!!))

            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _loginResponseLiveDataRepo.postValue(NetworkResult.Error(errorObj.getString("message")))


            } else {
                _loginResponseLiveDataRepo.postValue(NetworkResult.Error(".Something Wrong....."))

            }
        } catch (e: Exception) {
            Log.i("loginUserRepo", "loginUserRepo: ${e.localizedMessage}")
        }

    }

}