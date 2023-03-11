package com.marufalam.dufa.ui

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.R
import com.marufalam.dufa.databinding.FragmentDashboardBinding
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val dashboardViewModel by viewModels<DashboardViewModel>()

    override fun getFragmentView(): Int {
       return R.layout.fragment_dashboard
    }

    override fun configUi() {
        //dashboardViewModel.dashboardInfoVM()
        dashboardViewModel.profileInfoVM()

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
        dashboardViewModel.profileInfoVMLD.observe(viewLifecycleOwner) {
            //progressBar.isVisible = false
            when (it) {

                is NetworkResult.Error -> {
                    Log.i("Error", "NetworkResult.Error: ${it.data!!.profile.toString()}")
                    //Log.i("TAG1", "binObserver: ${it.data!!.message.toString()}")
                }
                is NetworkResult.Loading -> {
                   // progressBar.isVisible = true

                }
                is NetworkResult.Success -> {
                    Log.i("SuccessTAG", "DashboardSuccess: ${it.data!!.profile.toString()}")

                }


            }

        }
        dashboardViewModel.dashboardInfoVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.hide()
            when (it) {

                is NetworkResult.Error -> {

                    //Log.i("TAG1", "binObserver: ${it.data!!.message.toString()}")
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.show()

                }
                is NetworkResult.Success -> {
                    binding.totalMember.text = it.data?.total_member.toString()
                    binding.totalDues.text = it.data?.total_dues.toString()
                    binding.totalVouchers.text = it.data?.total_voucher.toString()



                }


            }

        }
    }


}