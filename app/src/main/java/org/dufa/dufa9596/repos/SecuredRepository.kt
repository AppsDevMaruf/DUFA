package org.dufa.dufa9596.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.dufa.dufa9596.api.SecuredApi
import org.dufa.dufa9596.data.models.dashboard.all_member.ResponseAllMember
import org.dufa.dufa9596.data.models.fund_collection.RequestFundCollection
import org.dufa.dufa9596.data.models.get_departments.ResponseDepartments
import org.dufa.dufa9596.data.models.locations.RequestSetCLocation
import org.dufa.dufa9596.data.models.payRenew.RequestPayRenew
import org.dufa.dufa9596.data.models.search.RequestSearch
import org.dufa.dufa9596.data.models.search.blood.ResponseBloodGroup
import org.dufa.dufa9596.data.models.transaction_history.TransHistory
import org.dufa.dufa9596.data.models.vouchers.ResponseVoucherList
import org.dufa.dufa9596.paging.MemberSearchPagingSource
import org.dufa.dufa9596.ui.profile_update.RequestProfileUpdate
import org.dufa.dufa9596.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class SecuredRepository @Inject constructor(private val securedApi: SecuredApi) {

    //get all member start
    private var _responseAllMember = MutableLiveData<NetworkResult<ResponseAllMember>>()

    val responseAllMember: LiveData<NetworkResult<ResponseAllMember>>
        get() = _responseAllMember

    suspend fun getAllMemberRepo() {

        _responseAllMember.postValue(NetworkResult.Loading())
        try {
            val response: Response<ResponseAllMember> =
                securedApi.getAllMember()
            if (response.isSuccessful && response.body() != null) {
                _responseAllMember.postValue(NetworkResult.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _responseAllMember.postValue(NetworkResult.Error(errorObj.getString("message")))
            } else {
                _responseAllMember.postValue(NetworkResult.Error("Something Went Wrong!"))
            }
        } catch (e: Exception) {
            Log.i("catch", "getAllMemberRepo: ${e.localizedMessage}")
        }


        //get all memberList end


    }


    //getTransactionHistory start
    private var _responseTransHistory = MutableLiveData<NetworkResult<TransHistory>>()

    val responseTransHistory: LiveData<NetworkResult<TransHistory>>
        get() = _responseTransHistory

    suspend fun getTransactionHistory() {

        _responseTransHistory.postValue(NetworkResult.Loading())
        try {
            val response: Response<TransHistory> =
                securedApi.getTransactionHistory()
            if (response.isSuccessful && response.body() != null) {
                _responseTransHistory.postValue(NetworkResult.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _responseTransHistory.postValue(NetworkResult.Error(errorObj.getString("message")))
            } else {
                _responseTransHistory.postValue(NetworkResult.Error("Something Went Wrong!"))
            }
        } catch (e: Exception) {
            Log.i("catch", "getAllMemberRepo: ${e.localizedMessage}")
        }



    }   //getTransactionHistory end

    //getVoucher List  start
    private var _responseVoucherList= MutableLiveData<NetworkResult<ResponseVoucherList>>()

    val responseVoucherList: LiveData<NetworkResult<ResponseVoucherList>>
        get() = _responseVoucherList

    suspend fun getVoucherListRepo() {

        _responseVoucherList.postValue(NetworkResult.Loading())
        try {
            val response: Response<ResponseVoucherList> =
                securedApi.getVoucherList()
            if (response.isSuccessful && response.body() != null) {
                _responseVoucherList.postValue(NetworkResult.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _responseVoucherList.postValue(NetworkResult.Error(errorObj.getString("message")))
            } else {
                _responseVoucherList.postValue(NetworkResult.Error("Something Went Wrong!"))
            }
        } catch (e: Exception) {
            Log.i("catch", "VoucherList: ${e.localizedMessage}")
        }

    }
        //getVoucher List end



        /*//My profile start
        private var _responseMyProfileRepo =
            MutableLiveData<NetworkResult<ResponseProfileInfo>>()
        val responseMyProfileRepo: LiveData<NetworkResult<ResponseProfileInfo>>
            get() = _responseMyProfileRepo

        fun getMyProfileRepo() {

            _responseMyProfileRepo.postValue(NetworkResult.Loading())
            try {
                val response = securedApi.getProfileInfo()

                if (response.isSuccessful && response.body() != null) {

                    _responseMyProfileRepo.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseMyProfileRepo.postValue(NetworkResult.Error(errorObj.getString("message")))

                } else {

                    _responseMyProfileRepo.postValue(NetworkResult.Error("Something Went Wrong!"))
                }
            } catch (e: Exception) {

                Log.i("catch", "DashboardApiRepo: ${e.localizedMessage}")

            }


        }
        //My profile end*/

    //get Departments start
    private var _responseDepartmentsRepo =
        MutableLiveData<NetworkResult<ResponseDepartments>>()
    val responseDepartmentsRepo: LiveData<NetworkResult<ResponseDepartments>>
        get() = _responseDepartmentsRepo

    suspend fun getDepartmentsRepo() {

        _responseDepartmentsRepo.postValue(NetworkResult.Loading())
        try {
            val response = securedApi.getDepartments()

            if (response.isSuccessful && response.body() != null) {

                _responseDepartmentsRepo.postValue(NetworkResult.Success(response.body()!!))

            } else if (response.errorBody() != null) {

                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _responseDepartmentsRepo.postValue(NetworkResult.Error(errorObj.getString("message")))

            } else {

                _responseDepartmentsRepo.postValue(NetworkResult.Error("Something Went Wrong!"))
            }
        } catch (e: Exception) {

            Log.i("catch", "getDepartmentsRepo: ${e.localizedMessage}")

        }


    }
    //get Departments start end

    // getBloodGroups start
    private var _responseBloodRepo =
        MutableLiveData<NetworkResult<ResponseBloodGroup>>()
    val responseBloodGroupRepo: LiveData<NetworkResult<ResponseBloodGroup>>
        get() = _responseBloodRepo

    suspend fun getBloodGroupsRepo() {

        _responseBloodRepo.postValue(NetworkResult.Loading())
        try {
            val response = securedApi.getBloodGroups()

            if (response.isSuccessful && response.body() != null) {

                _responseBloodRepo.postValue(NetworkResult.Success(response.body()!!))

            } else if (response.errorBody() != null) {

                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _responseBloodRepo.postValue(NetworkResult.Error(errorObj.getString("message")))

            } else {

                _responseBloodRepo.postValue(NetworkResult.Error("Something Went Wrong!"))
            }
        } catch (e: Exception) {

            Log.i("catch", "getDepartmentsRepo: ${e.localizedMessage}")

        }


    }
    //getBloodGroups start end


    fun getMemberSearchRepo(

        requestSearch: RequestSearch
    ) = Pager(
        config = PagingConfig(pageSize = 15, maxSize = 150),
        pagingSourceFactory = {
            MemberSearchPagingSource(
                securedApi,
                requestSearch
            )
        }
    ).liveData

    //suspend fun searchByNameEmail(nameOrEmail:RequestSearch) = securedApi.searchByNameEmail(nameOrEmail)
    suspend fun logout() = securedApi.logout()
    suspend fun getDashboardInfo() = securedApi.getDashboardInfo()
    suspend fun getProfile() = securedApi.getProfileInfo()
    suspend fun getDistricts() = securedApi.getDistricts()

    suspend fun getOccupations() = securedApi.getOccupations()
    suspend fun getHalls() = securedApi.getHalls()
    suspend fun uploadProfilePic(userId: Int, part: MultipartBody.Part) =
        securedApi.uploadProfilePic(userId, part)

    suspend fun updateProfile(userId: Int, requestProfileUpdate: RequestProfileUpdate) =
        securedApi.updateProfile(userId, requestProfileUpdate)

    suspend fun uploadVoucher(

        date: RequestBody,
        amount: RequestBody,
        voucher_number: RequestBody,
        part: MultipartBody.Part
    ) =
        securedApi.uploadVoucher(
            date = date,
            amount = amount,
            voucher_number = voucher_number,
            image = part
        )


    fun payRenew(requestPayRenew: RequestPayRenew) = securedApi.payRenew(requestPayRenew)
    fun fundCollection(fund: RequestFundCollection) = securedApi.fundPayment(fund)

    suspend fun setCurrentLocation( requestSetCLocation: RequestSetCLocation) = securedApi.setCurrentLocations(requestSetCLocation)

    suspend fun userLocations() = securedApi.getUserLocations()
    suspend fun getFeeListRepo() = securedApi.getFeeList()
}