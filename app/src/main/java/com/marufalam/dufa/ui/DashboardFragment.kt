package com.marufalam.dufa.ui

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.marufalam.dufa.R
import com.marufalam.dufa.databinding.FragmentDashboardBinding
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.data.local.TokenManager
import com.marufalam.dufa.data.models.getProfileInfo.Data
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val dashboardViewModel by viewModels<DashboardViewModel>()

    override fun getFragmentView(): Int {
       return R.layout.fragment_dashboard
    }

    override fun configUi() {
        dashboardViewModel.getMyProfileInfoVM()

    }

    override fun setupNavigation() {
        binding.memberListCard.setOnClickListener{
            findNavController().navigate(R.id.action_DashboardFragment_to_memberListFragment)
        }
        binding.paymentCard.setOnClickListener {
            toast("Thanks bro")
            findNavController().navigate(R.id.action_DashboardFragment_to_paymentFragment)
        }
        binding.eventFragment.setOnClickListener {
            toast("Thanks bro")
            findNavController().navigate(R.id.action_DashboardFragment_to_eventFragment)
        }


    }

    override fun binObserver() {

    }


}