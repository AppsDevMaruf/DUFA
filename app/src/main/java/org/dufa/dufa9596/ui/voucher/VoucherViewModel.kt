package org.dufa.dufa9596.ui.voucher

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.dufa.dufa9596.data.models.vouchers.ResponseVoucherList
import org.dufa.dufa9596.repos.SecuredRepository
import org.dufa.dufa9596.utils.NetworkResult
import org.dufa.dufa9596.utils.NoInternetException
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class VoucherViewModel @Inject constructor(private val securedRepository: SecuredRepository) :
    ViewModel() {


    //  voucher list start

    private var _responseVoucherList =
        MutableLiveData<NetworkResult<ResponseVoucherList>>()
    val voucherListVMLD: LiveData<NetworkResult<ResponseVoucherList>>
        get() = _responseVoucherList

    @SuppressLint("SuspiciousIndentation")
    fun voucherListVM() {

        _responseVoucherList.postValue(NetworkResult.Loading())

        viewModelScope.launch {

            try {
                val response = securedRepository.getVoucherListRepo()

                if (response.isSuccessful && response.body() != null) {


                    _responseVoucherList.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseVoucherList.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responseVoucherList.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }

        }

    }

    //   voucher list  end



}