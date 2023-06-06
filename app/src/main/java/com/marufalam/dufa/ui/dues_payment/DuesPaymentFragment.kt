package com.marufalam.dufa.ui.dues_payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
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
    private val dashboardViewModel by viewModels<DashboardViewModel>()
    val bundle = Bundle()

    var userid = 0
    private var dues: Double? = null
    private var lifetimeFee: Double? = null

    override fun getFragmentView(): Int {
        return R.layout.fragment_dues_payment
    }


    @SuppressLint("SetTextI18n")
    override fun configUi() {
        dashboardViewModel.getFeeListVM()


    }

    override fun setupNavigation() {

        binding.showHistory.setOnClickListener {
            findNavController().navigate(R.id.action_duesPaymentFragment_to_transactionHistoryFragment)
        }


        binding.continue2000Payment.setOnClickListener {
            val request = RequestPayRenew(
                amount = dues!!,
                membership = "yearly",
                userinfoID = userid,
                renewFee = "renew_fee"
            )
            dashboardViewModel.payRenewVM(request)
        }

        binding.continue10000Payment.setOnClickListener {
            val request = RequestPayRenew(
                amount = lifetimeFee!!.toDouble(),
                membership = "lifetime",
                userinfoID = userid,
                renewFee = "renew_fee"
            )
            dashboardViewModel.payRenewVM(request)
        }

    }

    @SuppressLint("SetTextI18n")
    override fun binObserver() {
        /*  dashboardViewModel.paymentDues.observe(viewLifecycleOwner) {

              if (it == 0) {
                  findNavController().navigate(R.id.action_duesPaymentFragment_to_transactionHistoryFragment)
                  binding.paymentsLayout.gone()

              } else {

                  binding.paymentsLayout.show()
                  binding.duesAmount.text = it.toString()
              }

          }*/


        dashboardViewModel.responsePayRenewVMLD.observe(this) {

            when (it) {

                is NetworkResult.Error -> {
                    Log.i("Error", "NetworkResult.Error: ${it.data.toString()}")
                }

                is NetworkResult.Loading -> {
                    // progressBar.isVisible = true
                }

                is NetworkResult.Success -> {
                    bundle.putString("paymentUrl", it.data.toString())
                    findNavController().navigate(
                        R.id.action_duesPaymentFragment_to_SSLFragment,
                        bundle
                    )


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


                }

            }

        }
        dashboardViewModel.getFeeListVMLD.observe(viewLifecycleOwner) {
            //progressBar.isVisible = false
            when (it) {

                is NetworkResult.Error -> {

                }

                is NetworkResult.Loading -> {
                    // progressBar.isVisible = true
                }

                is NetworkResult.Success -> {
                    lifetimeFee = it.data?.data?.get(1)?.fee
                    if (arguments != null) {
                        dues = requireArguments().getDouble("dues")
                    }
                    if (dues == 0.0) {
                        findNavController().navigate(R.id.action_duesPaymentFragment_to_transactionHistoryFragment)
                        binding.paymentsLayout.gone()

                    } else {
                        binding.paymentsLayout.show()
                        binding.duesAmount.text = "${dues.toString()} TK"
                        binding.lifetimeFee.text = "${lifetimeFee.toString()} TK"
                    }

                }

            }

        }

    }


}