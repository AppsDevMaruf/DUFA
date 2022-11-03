package com.marufalam.dufa.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marufalam.dufa.api.UserApi
import com.marufalam.dufa.models.login.RequestLogin
import com.marufalam.dufa.models.register.RequestRegister
import com.marufalam.dufa.models.register.ResponseRegister
import com.marufalam.dufa.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi){

    private val _registerResponseLiveDataRepo = MutableLiveData<NetworkResult<ResponseRegister>>()
    val registerResponseLiveDataRepo : LiveData<NetworkResult<ResponseRegister>>
        get() = _registerResponseLiveDataRepo

    suspend fun registerUserRepo(requestRegister: RequestRegister){
        _registerResponseLiveDataRepo.postValue(NetworkResult.Loading())
        val response = userApi.register(requestRegister)
        if (response.isSuccessful && response.body() != null){
            _registerResponseLiveDataRepo.postValue(NetworkResult.Success(response.body()!!))

        }else if (response.errorBody() != null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _registerResponseLiveDataRepo.postValue(NetworkResult.Error(errorObj.getString("message")))


        }else{
            _registerResponseLiveDataRepo.postValue(NetworkResult.Error(".Something Wrong....."))

        }


    }
    suspend fun loginUserRepo(requestLogin: RequestLogin){
        val response = userApi.login(requestLogin)
        Log.i("TAG", "loginUserRepo: "+response.body().toString())
    }

}