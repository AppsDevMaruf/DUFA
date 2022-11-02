package com.marufalam.dufa.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.R
import com.marufalam.dufa.databinding.FragmentLogInBinding
import com.marufalam.dufa.fragments.BaseFragment
import com.marufalam.dufa.models.login.RequestLogin
import com.marufalam.dufa.models.register.RequestRegister
import com.marufalam.dufa.viewmodel.AuthViewModel
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>() {
    private val authViewModel by viewModels<AuthViewModel>()

    override fun getFragmentView(): Int {
        return R.layout.fragment_log_in
    }

    override fun configUi() {
        binding.logIn.setOnClickListener {
            findNavController().navigate(R.id.action_Login_to_Dashboard)
        }
        binding.singup.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun setupNavigation() {

    }

    override fun binObserver() {

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


