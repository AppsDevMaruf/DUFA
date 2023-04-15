package com.marufalam.dufa.ui.trans_history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.marufalam.dufa.data.models.dashboard.dasboard_info.ResponseDashboardInfo
import com.marufalam.dufa.data.models.getProfileInfo.ResponseProfileInfo
import com.marufalam.dufa.data.models.get_districts.ResponseDistrict
import com.marufalam.dufa.data.models.get_halls.ResponseHalls
import com.marufalam.dufa.data.models.get_occupations.ResponseOccupations
import com.marufalam.dufa.data.models.logout.ResponseLogout
import com.marufalam.dufa.data.models.search.Data
import com.marufalam.dufa.data.models.search.RequestSearch
import com.marufalam.dufa.data.models.upload_profile_pic.ResponseUploadProfilePic
import com.marufalam.dufa.repos.SecuredRepository
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class TransationHistoryViewModel @Inject constructor(private val securedRepository: SecuredRepository) :
    ViewModel() {


    // get all Member start
    val getTransactionHistoryVMLD = securedRepository.responseTransHistory
    fun getTransactionHistoryVM() {
        viewModelScope.launch {
            securedRepository.getTransactionHistory()
        }
    }


}