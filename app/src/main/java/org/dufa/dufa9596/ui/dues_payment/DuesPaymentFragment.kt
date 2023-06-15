package org.dufa.dufa9596.ui.dues_payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import org.dufa.dufa9596.R
import org.dufa.dufa9596.data.models.payRenew.RequestPayRenew
import org.dufa.dufa9596.databinding.FragmentDuesPaymentBinding
import org.dufa.dufa9596.utils.NetworkResult
import org.dufa.dufa9596.utils.gone
import org.dufa.dufa9596.utils.show
import org.dufa.dufa9596.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.dufa.dufa9596.utils.showDialog

@AndroidEntryPoint
class DuesPaymentFragment : org.dufa.dufa9596.BaseFragment<FragmentDuesPaymentBinding>() {
    private val dashboardViewModel by viewModels<DashboardViewModel>()
    val bundle = Bundle()

    var userid = 0
    private var dues: Int? = null
    private var lifetimeFee: Int? = null

    override fun getFragmentView(): Int {
        return R.layout.fragment_dues_payment
    }


    @SuppressLint("SetTextI18n")
    override fun configUi() {
        dashboardViewModel.getFeeListVM()
        if (arguments != null) {
            dues = requireArguments().getInt("dues")
        }



        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_duesPaymentFragment_to_DashboardFragment)
        }

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
                amount = lifetimeFee!!,
                membership = "lifetime",
                userinfoID = userid,
                renewFee = "renew_fee"
            )
            dashboardViewModel.payRenewVM(request)
        }

    }

    @SuppressLint("SetTextI18n")
    override fun binObserver() {

        dashboardViewModel.responsePayRenewVMLD.observe(this) {
            binding.progressBar.gone()
            when (it) {

                is NetworkResult.Error -> {
                    showDialog(
                        context = requireContext(),
                        title = "",
                        details = "${it.message}",
                        resId = R.drawable.ic_round_warning_24,
                        yesContent = "Okay",
                        noContent = "Cancel",
                        showNoBtn = false,
                        positiveFun = {
                        }, {}
                    )
                }

                is NetworkResult.Loading -> {
                   binding.progressBar.show()
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
                    if (dues == 0) {
                        binding.paymentsLayout.gone()
                        findNavController().navigate(R.id.action_duesPaymentFragment_to_transactionHistoryFragment)

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