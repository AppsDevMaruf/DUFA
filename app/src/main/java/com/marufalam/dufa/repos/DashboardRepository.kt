package com.marufalam.dufa.repos

import android.util.JsonToken
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marufalam.dufa.api.DashboardApi
import com.marufalam.dufa.data.models.dashboard.ResponseAllMember
import com.marufalam.dufa.data.models.getProfileInfo.ResponseProfileInfo
import com.marufalam.dufa.data.models.get_departments.ResponseDepartments
import com.marufalam.dufa.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class DashboardRepository @Inject constructor(private val dashboardApi: DashboardApi) {

    //get all member start
    private var _responseAllMember = MutableLiveData<NetworkResult<ResponseAllMember>>()

    val responseAllMember: LiveData<NetworkResult<ResponseAllMember>>
        get() = _responseAllMember

    suspend fun getAllMemberRepo() {

        _responseAllMember.postValue(NetworkResult.Loading())
        try {
            val response: Response<ResponseAllMember> =
                dashboardApi.getAllMember()
            if (response.isSuccessful && response.body() != null) {
                _responseAllMember.postValue(NetworkResult.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _responseAllMember.postValue(NetworkResult.Error(errorObj.getString("message")))
            } else {
                _responseAllMember.postValue(NetworkResult.Error("Something Went Wrong!"))
            }
        }catch (e:Exception){
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
            val response = dashboardApi.getProfileInfo()

            if (response.isSuccessful && response.body() != null) {

                _responseMyProfileRepo.postValue(NetworkResult.Success(response.body()!!))

            } else if (response.errorBody() != null) {

                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _responseMyProfileRepo.postValue(NetworkResult.Error(errorObj.getString("message")))

            } else {

                _responseMyProfileRepo.postValue(NetworkResult.Error("Something Went Wrong!"))
            }
        }catch (e:Exception){

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
            val response = dashboardApi.getDepartments()

            if (response.isSuccessful && response.body() != null) {

                _responseDepartmentsRepo.postValue(NetworkResult.Success(response.body()!!))

            } else if (response.errorBody() != null) {

                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _responseDepartmentsRepo.postValue(NetworkResult.Error(errorObj.getString("message")))

            } else {

                _responseDepartmentsRepo.postValue(NetworkResult.Error("Something Went Wrong!"))
            }
        }catch (e:Exception){

            Log.i("catch", "getDepartmentsRepo: ${e.localizedMessage}")

        }


    }
    //get Departments start end



//    //get memberList by Search
//    private var _responseSearchMemberList = MutableLiveData<NetworkResult<ResponseMemberList>>()
//
//    val responseSearchMemberList: LiveData<NetworkResult<ResponseMemberList>>
//        get() = _responseSearchMemberList

/*
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
*/


}