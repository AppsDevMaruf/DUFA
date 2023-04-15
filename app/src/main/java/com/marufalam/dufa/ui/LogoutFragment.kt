package com.marufalam.dufa.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.data.local.TokenManager
import com.marufalam.dufa.databinding.FragmentLogoutBinding
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.toast
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LogoutFragment : BaseFragment<FragmentLogoutBinding>() {
    private val dashboardViewModel by viewModels<DashboardViewModel>()

    override fun getFragmentView(): Int {
        return R.layout.fragment_logout
    }

    override fun configUi() {

        toast("Thanks ")
        //dashboardViewModel.logoutVM()
    }

    override fun setupNavigation() {

    }

    override fun binObserver() {
        dashboardViewModel.logoutVMLD.observe(this) {
            //progressBar.isVisible = false
            when (it) {

                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {
                    // progressBar.isVisible = true

                }
                is NetworkResult.Success -> {

                    toast(it.data!!.message)
                    Log.i("logout", "DashboardSuccess: ${it.data!!.message}")

                }


            }

        }
    }

}