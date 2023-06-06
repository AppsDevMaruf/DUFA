package org.dufa.dufa9596.ui.trans_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.dufa.dufa9596.repos.SecuredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionHistoryViewModel @Inject constructor(private val securedRepository: SecuredRepository) :
    ViewModel() {


    // get all Member start
    val getTransactionHistoryVMLD = securedRepository.responseTransHistory
    fun getTransactionHistoryVM() {
        viewModelScope.launch {
            securedRepository.getTransactionHistory()
        }
    }

    private val _dueAmountVMLD = MutableLiveData<Int>()
    val dueAmountVMLD: LiveData<Int> = _dueAmountVMLD


    fun setDueAmount(totalDue: Int) {

        _dueAmountVMLD.postValue(totalDue)

    }


}