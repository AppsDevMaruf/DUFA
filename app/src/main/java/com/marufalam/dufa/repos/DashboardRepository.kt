package com.marufalam.dufa.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marufalam.dufa.api.DashboardApi
import com.marufalam.dufa.models.dashboard.ResponseMemberList
import com.marufalam.dufa.utils.Constants
import com.marufalam.dufa.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class DashboardRepository @Inject constructor(private val dashboardApi: DashboardApi) {

    //get memberList start
    private var _responsegetMemberList = MutableLiveData<NetworkResult<ResponseMemberList>>()

    val responsegetMemberList: LiveData<NetworkResult<ResponseMemberList>>
        get() = _responsegetMemberList

    suspend fun getMemberListRepo() {

        _responsegetMemberList.postValue(NetworkResult.Loading())

        val response: Response<ResponseMemberList> =
            dashboardApi.getUserinfos()
        if (response.isSuccessful && response.body() != null) {
            _responsegetMemberList.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _responsegetMemberList.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _responsegetMemberList.postValue(NetworkResult.Error("Something Went Wrong!"))
        }
        //get memberList end


    }


//    //get memberList by Search
//    private var _responseSearchMemberList = MutableLiveData<NetworkResult<ResponseMemberList>>()
//
//    val responseSearchMemberList: LiveData<NetworkResult<ResponseMemberList>>
//        get() = _responseSearchMemberList

    suspend fun getSearchMemberListRepo(search: String, type: String) {

        _responsegetMemberList.postValue(NetworkResult.Loading())

        val response: Response<ResponseMemberList>

        when (type) {
            Constants.BY_DISTRICT -> {
                response = dashboardApi.getUserByDistrict(search)
                getResponse(response)
            }
            Constants.BY_DEPARTMENT -> {
                response = dashboardApi.getUserByDepartment(search)
                getResponse(response)
            }
            Constants.BY_BLOOD -> {
                response = dashboardApi.getUserByBloodGroup(search)
                getResponse(response)
            }
            Constants.BY_PROFESSION -> {
                response = dashboardApi.getUserByProfession(search)
                getResponse(response)
            }
        }


        //get memberList end


    }


    private fun getResponse(response: Response<ResponseMemberList>) {
        if (response.isSuccessful && response.body() != null) {
            _responsegetMemberList.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _responsegetMemberList.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _responsegetMemberList.postValue(NetworkResult.Error("Something Went Wrong!"))
        }

    }


}