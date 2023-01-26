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
import com.marufalam.dufa.databinding.FragmentEventBinding
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventFragment : BaseFragment<FragmentEventBinding>() {
    private val dashboardViewModel by viewModels<DashboardViewModel>()

    override fun getFragmentView(): Int {
     return R.layout.fragment_event
    }

    override fun configUi() {
        dashboardViewModel.getDepartmentsVM()
    }

    override fun setupNavigation() {

    }

    override fun binObserver() {
        dashboardViewModel.getDepartmentsVMLD.observe(this) {
            //progressBar.isVisible = false
            when (it) {

                is NetworkResult.Error -> {
                    Log.i("Error", "NetworkResult.Error: ${it.data!!.departments.toString()}")
                    binding.textView.text = it.data.departments!![0]!!.name
                }
                is NetworkResult.Loading -> {
                    // progressBar.isVisible = true

                }
                is NetworkResult.Success -> {
                    Log.i("SuccessTAG", "DashboardSuccess: ${it.data!!.departments.toString()}")
                    binding.textView.text = it.data.departments!![0]!!.name


                }


            }

        }

    }


}