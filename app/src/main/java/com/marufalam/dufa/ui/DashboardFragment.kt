package com.marufalam.dufa.ui

import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.R
import com.marufalam.dufa.databinding.FragmentDashboardBinding
import com.marufalam.dufa.fragments.BaseFragment


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