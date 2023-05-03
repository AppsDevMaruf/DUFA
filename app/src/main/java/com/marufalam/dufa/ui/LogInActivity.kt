package com.marufalam.dufa.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.marufalam.dufa.MainActivity
import com.marufalam.dufa.data.local.TokenManager
import com.marufalam.dufa.data.models.login.RequestLogin
import com.marufalam.dufa.databinding.FragmentLogInBinding
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModels<AuthViewModel>()

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
        binding.logIn.setOnClickListener {
            binding.loginErrorText.isVisible = false
            //debug
            val email = "masummehedi1@gmail.com"
            val password = "12345678"

            val loginRequestLogin = RequestLogin(email, password)

            lifecycleScope.launch {
                authViewModel.loginUserVM(loginRequestLogin)
            }


            //production
//            if (!isValidEmail(binding.loginEmail.text.toString().trim())) {
//
//                Log.i("TAG", "onCreate: Clicked ")
//                binding.loginErrorText.error = "Email Pattern is Not Correct !"
//                binding.loginErrorText.isVisible = true
//
//
//            } else if (binding.passwordInput.text.toString().trim() == "") {
//                binding.loginErrorText.error = "Password Required!"
//                binding.loginErrorText.isVisible = true
//
//            } else if (binding.passwordInput.text.toString().trim().length < 8) {
//
//                binding.loginErrorText.error = "Password Length Minimum 8 char"
//                binding.loginErrorText.isVisible = true
//
//
//            } else {
//                val email = binding.loginEmail.text.toString().trim()
//                val password = binding.passwordInput.text.toString().trim()
//
//
//
//                val loginRequestLogin = RequestLogin(email, password)
//
//                lifecycleScope.launch {
//                    authViewModel.loginUserVM(loginRequestLogin)
//                }
//
//
//            }
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



