package com.marufalam.dufa.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.marufalam.dufa.api.SecuredApi
import com.marufalam.dufa.data.models.dashboard.ResponseAllMember
import com.marufalam.dufa.data.models.getProfileInfo.ResponseProfileInfo
import com.marufalam.dufa.data.models.get_departments.ResponseDepartments
import com.marufalam.dufa.paging.MemberSearchPagingSource
import com.marufalam.dufa.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import androidx.paging.liveData
import com.marufalam.dufa.data.models.search.RequestSearch

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

    //My profile start
    private var _responseMyProfileRepo =
        MutableLiveData<NetworkResult<ResponseProfileInfo>>()
    val responseMyProfileRepo: LiveData<NetworkResult<ResponseProfileInfo>>
        get() = _responseMyProfileRepo

    suspend fun getMyProfileRepo() {

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
    //My profile end

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

    suspend fun logout() = securedApi.logout()
    suspend fun getDashboardInfo() = securedApi.getDashboardInfo()


}