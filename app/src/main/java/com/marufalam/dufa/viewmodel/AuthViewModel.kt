package com.marufalam.dufa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marufalam.dufa.data.models.login.RequestLogin
import com.marufalam.dufa.data.models.login.ResponseLogin
import com.marufalam.dufa.data.models.register.RequestRegister
import com.marufalam.dufa.data.models.register.ResponseRegister
import com.marufalam.dufa.repos.UserRepository
import com.marufalam.dufa.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) :ViewModel() {
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
}