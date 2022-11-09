package com.marufalam.dufa.ui

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.R
import com.marufalam.dufa.databinding.FragmentLogInBinding
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.models.login.RequestLogin
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.TokenManager
import com.marufalam.dufa.utils.isValidEmail
import com.marufalam.dufa.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>() {
    private val authViewModel by viewModels<AuthViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager

    override fun getFragmentView(): Int {
        return R.layout.fragment_log_in
    }

    override fun configUi() {
        binding.logIn.setOnClickListener {
            binding.loginErrorText.isVisible =false
            if (!isValidEmail(binding.loginEmail.text.toString().trim())) {

                Log.i("TAG", "onCreate: Clicked ")
                binding.loginErrorText.error = "Email Pattern is Not Correct !"
                binding.loginErrorText.isVisible =true


            } else if (binding.passwordInput.text.toString().trim() == "") {
                binding.loginErrorText.error = "Password Required!"
                binding.loginErrorText.isVisible =true

            } else if (binding.passwordInput.text.toString().trim().length < 8) {

                binding.loginErrorText.error = "Password Length Minimum 8 char"
                binding.loginErrorText.isVisible =true



            } else {

                val email = binding.loginEmail.text.toString().trim()
                val password = binding.passwordInput.text.toString().trim()

                val loginRequestLogin = RequestLogin(email, password)

                lifecycleScope.launch {
                    authViewModel.loginUserVM(loginRequestLogin)
                }


            }
        }
        binding.singup.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
        }

    }

    override fun setupNavigation() {
        if (tokenManager.getToken()!=null){
            findNavController().navigate(R.id.action_logInFragment_to_DashboardFragment)
        }

    }

    override fun binObserver() {
        authViewModel.loginResponseLiveDataVM.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    //token
                    tokenManager.saveToken(it.data!!.token)
                    Log.e("TAG", "binObserver: ${it.data.token}")
                    findNavController().navigate(R.id.action_logInFragment_to_DashboardFragment)
                }
                is NetworkResult.Error -> {
                    binding.loginErrorText.visibility = View.VISIBLE
                    binding.loginErrorText.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true

                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}

//


