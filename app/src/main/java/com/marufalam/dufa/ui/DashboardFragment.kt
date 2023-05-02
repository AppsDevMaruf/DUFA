package com.marufalam.dufa.ui

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.databinding.FragmentDashboardBinding
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.hide
import com.marufalam.dufa.utils.show
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val dashboardViewModel by activityViewModels<DashboardViewModel>()

    override fun getFragmentView(): Int {
        return R.layout.fragment_dashboard
    }

    override fun configUi() {
        dashboardViewModel.profileInfoVM()
        dashboardViewModel.dashboardInfoVM()


    }

    override fun setupNavigation() {
        binding.memberListCard.setOnClickListener {
            findNavController().navigate(R.id.action_DashboardFragment_to_memberListFragment)
        }
        binding.duesPaymentBtn.setOnClickListener {

            findNavController().navigate(R.id.action_DashboardFragment_to_duesPaymentFragment)
        }

        binding.userQr.setOnClickListener {

            findNavController().navigate(R.id.action_DashboardFragment_to_QRFragment)


        }


    }

    override fun binObserver() {
        dashboardViewModel.profileInfoVMLD.observe(viewLifecycleOwner) {
            //progressBar.isVisible = false
            when (it) {

                is NetworkResult.Error -> {
                    Log.i("Error", "NetworkResult.Error: ${it.data!!}")
                    //Log.i("TAG1", "binObserver: ${it.data!!.message.toString()}")
                }
                is NetworkResult.Loading -> {
                    // progressBar.isVisible = true

                }
                is NetworkResult.Success -> {
                    Log.i("SuccessTAG", "DashboardSuccess: ${it.data!!}")

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

                    it.data?.totalDues?.let { it1 -> dashboardViewModel.savePaymentDues(it1) }
                    binding.totalMember.text = it.data?.totalMember.toString()
                    binding.totalDues.text = it.data?.totalDues.toString()
                    binding.totalVouchers.text = it.data?.totalVoucher.toString()


                }


            }

        }
    }


}