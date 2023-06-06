package org.dufa.dufa9596.ui.dues_payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import org.dufa.dufa9596.BaseFragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.data.models.payRenew.RequestPayRenew
import org.dufa.dufa9596.databinding.FragmentDuesPaymentBinding
import org.dufa.dufa9596.utils.NetworkResult
import org.dufa.dufa9596.utils.gone
import org.dufa.dufa9596.utils.show
import org.dufa.dufa9596.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DuesPaymentFragment : org.dufa.dufa9596.BaseFragment<FragmentDuesPaymentBinding>() {
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