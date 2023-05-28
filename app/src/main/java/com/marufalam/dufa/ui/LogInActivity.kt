package com.marufalam.dufa.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.marufalam.dufa.MainActivity
import com.marufalam.dufa.data.local.TokenManager
import com.marufalam.dufa.data.models.locations.RequestSetCLocation
import com.marufalam.dufa.data.models.login.RequestLogin
import com.marufalam.dufa.databinding.FragmentLogInBinding
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {
    private val authViewModel by viewModels<AuthViewModel>()
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var userId :Int = 0
    private val permissionId = 2
    lateinit var binding: FragmentLogInBinding

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configUi()
        binObserver()
    }

    fun configUi() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.logIn.setOnClickListener {

            binding.loginErrorText.isVisible = false
            //debug
            //masummehedi1
            val email = "masummehedi1@gmail.com"
            val password = "12345678"

            val loginRequestLogin = RequestLogin(email, password)

            lifecycleScope.launch {
                authViewModel.loginUserVM(loginRequestLogin)
            }
            //debug end


            //production
           /*  if (!isValidEmail(binding.loginEmail.text.toString().trim())) {

                 Log.i("TAG", "onCreate: Clicked ")
                 binding.loginErrorText.error = "Email Pattern is Not Correct !"
                 binding.loginErrorText.isVisible = true


             } else if (binding.passwordInput.text.toString().trim() == "") {
                 binding.loginErrorText.error = "Password Required!"
                 binding.loginErrorText.isVisible = true

             } else if (binding.passwordInput.text.toString().trim().length < 8) {

                 binding.loginErrorText.error = "Password Length Minimum 8 char"
                 binding.loginErrorText.isVisible = true


             } else {
                 val email = binding.loginEmail.text.toString().trim()
                 val password = binding.passwordInput.text.toString().trim()



                 val loginRequestLogin = RequestLogin(email, password)

                 lifecycleScope.launch {
                     authViewModel.loginUserVM(loginRequestLogin)
                 }


             }*/
            //production end
        }


    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())

                        val list: List<Address> = geocoder.getFromLocation(
                            location.latitude,
                            location.longitude,
                            1
                        ) as List<Address>
                        Log.i(
                            "TAG",
                            "LoginPageGetLocation: ${list[0].latitude}\n${list[0].longitude}"
                        )

                        val requestSetCLocation = RequestSetCLocation(
                            userId,
                            cityName = list[0].adminArea,
                            latitude = list[0].latitude,
                            longitude = list[0].longitude
                        )
                        Log.i("TAG", "requestSetCLocation: $requestSetCLocation")
                        authViewModel.setCurrentLocationVM(requestSetCLocation)
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    fun binObserver() {
        authViewModel.loginResponseLiveDataVM.observe(this) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    getLocation()

                    //token
                    Log.e("SuccessToken", "binObserver: ${it.data}")
                    authViewModel.setLoginResponseToken(it.data!!)

                    tokenManager.saveToken(Constants.TOKEN, it.data.token)
                    userId = it.data.user_id

                    startActivity(Intent(this@LogInActivity, MainActivity::class.java))
                    finish()
                }
                is NetworkResult.Error -> {
                    binding.loginErrorText.show()
                    binding.loginErrorText.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }
            }
        }
        authViewModel.setCurrentLocationVMLD.observe(this) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    Log.i("TAG", "setCurrentLocationVMLD: ${ it.data?.message }")
                }
                is NetworkResult.Error -> {
                    binding.loginErrorText.show()
                    binding.loginErrorText.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }
            }
        }

    }


//     fun onResume() {
//        super.onResume()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
//    }
//
//     fun onStop() {
//        super.onStop()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
//    }
}



