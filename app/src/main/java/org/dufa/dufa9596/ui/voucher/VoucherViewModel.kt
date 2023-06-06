package org.dufa.dufa9596.ui.voucher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.dufa.dufa9596.repos.SecuredRepository
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