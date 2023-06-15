package org.dufa.dufa9596.ui.trans_history

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.dufa.dufa9596.repos.SecuredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.dufa.dufa9596.data.models.transaction_history.TransHistory
import org.dufa.dufa9596.data.models.vouchers.ResponseVoucherList
import org.dufa.dufa9596.utils.NetworkResult
import org.dufa.dufa9596.utils.NoInternetException
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class TransactionHistoryViewModel @Inject constructor(private val securedRepository: SecuredRepository) :
    ViewModel() {

//  transactionHistory start

    private var _responseTransactionHistory =
        MutableLiveData<NetworkResult<TransHistory>>()
    val transactionHistoryVMLD: LiveData<NetworkResult<TransHistory>>
        get() = _responseTransactionHistory

    @SuppressLint("SuspiciousIndentation")
    fun transactionHistoryVM() {

        _responseTransactionHistory.postValue(NetworkResult.Loading())

        viewModelScope.launch {

            try {
                val response = securedRepository.getTransactionHistory()

                if (response.isSuccessful && response.body() != null) {


                    _responseTransactionHistory.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseTransactionHistory.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responseTransactionHistory.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }

        }

    }
    //  transactionHistory end



}