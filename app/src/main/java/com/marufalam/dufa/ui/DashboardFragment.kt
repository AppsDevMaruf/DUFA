package com.marufalam.dufa.ui

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.R
import com.marufalam.dufa.databinding.FragmentDashboardBinding
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.hide
import com.marufalam.dufa.utils.show
import com.marufalam.dufa.utils.showAlert
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {


    override fun getFragmentView(): Int {
       return R.layout.fragment_dashboard
    }

    override fun configUi() {


        binding.memberListCard.setOnClickListener{
            findNavController().navigate(R.id.action_DashboardFragment_to_memberListFragment)
        binding.paymentCard.setOnClickListener {
        findNavController().navigate(R.id.action_DashboardFragment_to_paymentFragment)
        }

        }

    }

    override fun setupNavigation() {

    }

    override fun binObserver() {

    }



}