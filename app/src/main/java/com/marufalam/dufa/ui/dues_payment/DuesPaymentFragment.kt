package com.marufalam.dufa.ui.dues_payment

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.data.models.payRenew.RequestPayRenew
import com.marufalam.dufa.databinding.FragmentDuesPaymentBinding
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.gone
import com.marufalam.dufa.utils.show
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DuesPaymentFragment : BaseFragment<FragmentDuesPaymentBinding>() {
    private val dashboardViewModel by activityViewModels<DashboardViewModel>()

    var userid = 0
    override fun getFragmentView(): Int {
        return R.layout.fragment_dues_payment
    }


    override fun configUi() {


    }

    override fun setupNavigation() {

        binding.showHistory.setOnClickListener {

            findNavController().navigate(R.id.action_duesPaymentFragment_to_transactionHistoryFragment)

        }



        binding.continue2000Payment.setOnClickListener {
            val request = RequestPayRenew(
                amount = binding.duesAmount.text.toString().toInt(),
                membership = "yearly",
                userinfoID = userid,
                renewFee = "renew_fee"
            )
            dashboardViewModel.payRenewVM(request)
        }

        binding.continue10000Payment.setOnClickListener {
            val request = RequestPayRenew(
                amount = 10000,
                membership = "yearly",
                userinfoID = userid,
                renewFee = "renew_fee"
            )
            dashboardViewModel.payRenewVM(request)
        }

    }

    override fun binObserver() {
        dashboardViewModel.paymentDues.observe(viewLifecycleOwner) {

            if (it == 0) {
                binding.paymentsLayout.gone()
            } else {

                binding.paymentsLayout.show()
                binding.duesAmount.text = it.toString()

            }


        }



        dashboardViewModel.responsePayRenewVMLD.observe(this) {

            when (it) {

                is NetworkResult.Error -> {
                    Log.i("Error", "NetworkResult.Error: ${it.data.toString()}")
                }

                is NetworkResult.Loading -> {
                    // progressBar.isVisible = true

                }
                is NetworkResult.Success -> {


                    dashboardViewModel.savePaymentUrl(it.data.toString())
                    findNavController().navigate(R.id.action_duesPaymentFragment_to_SSLFragment)


                }

            }

        }

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
                    userid = it.data?.id!!


                    Log.i("SuccessTAG", "DashboardSuccess: ${it.data?.id}")

                }

            }

        }

    }


}