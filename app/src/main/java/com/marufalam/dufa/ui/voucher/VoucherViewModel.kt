package com.marufalam.dufa.ui.voucher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marufalam.dufa.repos.SecuredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoucherViewModel @Inject constructor(private val securedRepository: SecuredRepository) :
    ViewModel() {


    // getVoucherList
    val getVoucherListVMLD = securedRepository.responseVoucherList
    fun getVoucherListVM() {
        viewModelScope.launch {
            securedRepository.getVoucherListRepo()
        }
    }
    // getVoucherList end



}