package com.marufalam.dufa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marufalam.dufa.data.models.locations.RequestSetCLocation
import com.marufalam.dufa.data.models.locations.ResponseSetCLocantion
import com.marufalam.dufa.data.models.login.RequestLogin
import com.marufalam.dufa.data.models.login.ResponseLogin
import com.marufalam.dufa.data.models.register.RequestRegister
import com.marufalam.dufa.data.models.register.ResponseRegister
import com.marufalam.dufa.repos.PublicRepository
import com.marufalam.dufa.repos.SecuredRepository
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: PublicRepository,private val securedRepository: SecuredRepository) :ViewModel() {

    private val _loginResponseToken = MutableLiveData<ResponseLogin>()
    val loginResponseToken: LiveData<ResponseLogin>
        get() = _loginResponseToken

    fun setLoginResponseToken(responseLogin: ResponseLogin) {
        _loginResponseToken.postValue(responseLogin)

    }

    val registerResponseLiveDataVM :LiveData<NetworkResult<ResponseRegister>>
    get() = userRepository.registerResponseLiveDataRepo

    val loginResponseLiveDataVM :LiveData<NetworkResult<ResponseLogin>>
        get() = userRepository.loginResponseLiveDataRepo



    fun registerUserVM(requestRegister: RequestRegister){
       viewModelScope.launch {
           userRepository.registerUserRepo(requestRegister)
       }

    }
    suspend fun loginUserVM(requestLogin: RequestLogin){
        viewModelScope.launch {
            userRepository.loginUserRepo(requestLogin)
        }

    }

    //  setCurrentLocation  start

    private var _setCurrentLocation =
        MutableLiveData<NetworkResult<ResponseSetCLocantion>>()
    val setCurrentLocationVMLD: LiveData<NetworkResult<ResponseSetCLocantion>>
        get() = _setCurrentLocation

   fun setCurrentLocationVM(setCLocation: RequestSetCLocation) {

        _setCurrentLocation.postValue(NetworkResult.Loading())


        viewModelScope.launch {

            try {
                val response = securedRepository.setCurrentLocation(setCLocation)

                if (response.isSuccessful && response.body() != null) {


                    _setCurrentLocation.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _setCurrentLocation.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _setCurrentLocation.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }

        }

    }
    //   setCurrentLocation  end
}