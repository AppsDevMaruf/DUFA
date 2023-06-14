package org.dufa.dufa9596.ui

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
import org.dufa.dufa9596.MainActivity
import org.dufa.dufa9596.data.local.TokenManager
import org.dufa.dufa9596.data.models.locations.RequestSetCLocation
import org.dufa.dufa9596.data.models.login.RequestLogin
import org.dufa.dufa9596.databinding.FragmentLogInBinding
import org.dufa.dufa9596.utils.*
import org.dufa.dufa9596.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {
    private val authViewModel by viewModels<AuthViewModel>()
    private var userId: Int = 0
    lateinit var binding: FragmentLogInBinding

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (tokenManager.getToken(Constants.TOKEN) != Constants.NO_DATA) {

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        configUi()
        binObserver()
    }

    fun configUi() {

        binding.logIn.setOnClickListener {

            binding.loginErrorText.isVisible = false
            //debug
            //masummehedi1

            /*           val email = "masummehedi1@gmail.com"
                       val password = "12345678"

                       val loginRequestLogin = RequestLogin(email, password)

                       lifecycleScope.launch {
                           authViewModel.loginUserVM(loginRequestLogin)
                       }*/
            //debug end


            //production
            if (!isValidEmail(binding.loginEmail.text.toString().trim())) {

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


            }
            //production end
        }


    }
    fun binObserver() {
        authViewModel.loginResponseLiveDataVM.observe(this) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {

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



