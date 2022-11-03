package com.marufalam.dufa.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordstrengthcalculator.PasswordStrengthCalculator
import com.marufalam.dufa.R
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.passwordstrengthcalculator.StrengthLevel
import com.marufalam.dufa.databinding.FragmentSignUpBinding
import com.marufalam.dufa.fragments.BaseFragment
import com.marufalam.dufa.fragments.signup.InputValidation
import com.marufalam.dufa.models.login.RequestLogin
import com.marufalam.dufa.models.register.RequestRegister
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    private val authViewModel by viewModels<AuthViewModel>()


    override fun getFragmentView(): Int {
        return R.layout.fragment_sign_up
    }

    override fun configUi() {
        binding.signUpBtn.setOnClickListener {
            authViewModel.registerUserVM(
                RequestRegister(
                    "murad",
                    "murtyb@gmail.com",
                    "01644577482",
                    "Dhaka",
                    "11223344",
                    "11223344"
                )
            )

        }
        binding.loginText.setOnClickListener {
            //findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
            lifecycleScope.launch {
                authViewModel.loginUserVM(RequestLogin("murad@gmail.com", "11223344"))
            }

        }


    }

    override fun setupNavigation() {

    }

    override fun binObserver() {
        authViewModel.registerResponseLiveDataVM.observe(viewLifecycleOwner, Observer {
           binding.progressBar.isVisible =false
            when (it) {
                is NetworkResult.Success -> {
                    //token abi baki he...
                    findNavController().navigate(R.id.action_signUpFragment_to_DashboardFragment)
                }
                is NetworkResult.Error -> {
                    binding.signupErrorText.visibility =View.VISIBLE
                    binding.signupErrorText.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true

                }
            }
        })

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