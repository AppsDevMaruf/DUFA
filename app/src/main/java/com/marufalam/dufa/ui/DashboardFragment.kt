package com.marufalam.dufa.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.databinding.FragmentDashboardBinding
import com.marufalam.dufa.ui.trans_history.TransationHistoryViewModel
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.hide
import com.marufalam.dufa.utils.show
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val dashboardViewModel by viewModels<DashboardViewModel>()
    private val transactionHistoryViewModel by activityViewModels<TransationHistoryViewModel>()

    var totalDues = 0


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


            transactionHistoryViewModel.setDueAmount(binding.totalDues.text.toString().toInt())

            Log.i("TAG", "setupNavigation: ")

            totalDues=binding.totalDues.text.toString().toInt()


            val bundle = Bundle()

            bundle.putInt("totalDues", totalDues)



            findNavController().navigate(
                R.id.action_DashboardFragment_to_duesPaymentFragment,
                bundle
            )
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
                    it.data
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


                    binding.totalMember.text = it.data?.totalMember.toString()
                    binding.totalDues.text = it.data?.totalDues.toString()
                    binding.totalVouchers.text = it.data?.totalVoucher.toString()


                }


            }

        }
    }


}