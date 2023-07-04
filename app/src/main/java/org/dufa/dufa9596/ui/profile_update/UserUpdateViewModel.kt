package org.dufa.dufa9596.ui.profile_update
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.dufa.dufa9596.data.models.profile.RequestProfileUpdate
import org.dufa.dufa9596.data.models.upload_profile_pic.ResponseUploadProfilePic
import org.dufa.dufa9596.repos.SecuredRepository
import org.dufa.dufa9596.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class UserUpdateViewModel @Inject constructor(private val securedRepository: SecuredRepository) : ViewModel() {
    private var _responseUpdateProfile =
        MutableLiveData<NetworkResult<ResponseUploadProfilePic>>()
    val responseUpdateProfileVMLD: LiveData<NetworkResult<ResponseUploadProfilePic>>
        get() = _responseUpdateProfile

    suspend fun updateProfileVM(userId: Int, requestProfileUpdate: RequestProfileUpdate) {

        _responseUpdateProfile.postValue(NetworkResult.Loading())


        viewModelScope.launch {

            try {
                val response = securedRepository.updateProfile(userId, requestProfileUpdate)

                Log.i("TAG", "uploadProfilePicVM: $response")

                if (response.isSuccessful && response.body() != null) {


                    _responseUpdateProfile.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseUpdateProfile.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: Exception) {
                _responseUpdateProfile.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }

        }

    }
}