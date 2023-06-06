package org.dufa.dufa9596.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.dufa.dufa9596.BaseFragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.data.local.TokenManager
import org.dufa.dufa9596.databinding.FragmentDashboardBinding
import org.dufa.dufa9596.utils.Constants
import org.dufa.dufa9596.utils.NetworkResult
import org.dufa.dufa9596.utils.gone
import org.dufa.dufa9596.utils.show
import org.dufa.dufa9596.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val dashboardViewModel by activityViewModels<DashboardViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager
    private var dues: Double? = null
    private var bundle = Bundle()
    override fun getFragmentView(): Int {
        return R.layout.fragment_dashboard
    }

    override fun configUi() {


        binding.progressBar.show()
        binding.mainLayout.gone()
        dashboardViewModel.dashboardInfoVM()


    }

    override fun setupNavigation() {
        binding.memberListCard.setOnClickListener {
            findNavController().navigate(R.id.action_DashboardFragment_to_memberListFragment)
        }
        binding.paymentCard.setOnClickListener {


            if (dues!! > 0) {
                bundle.putDouble("dues", dues!!)
                findNavController().navigate(
                    R.id.action_DashboardFragment_to_duesPaymentFragment, bundle
                )
            } else {
                findNavController().navigate(R.id.action_DashboardFragment_to_transactionHistoryFragment)
            }

        }

        binding.userQr.setOnClickListener {

            findNavController().navigate(R.id.action_DashboardFragment_to_QRFragment)


        }

        binding.voucher.setOnClickListener {

            findNavController().navigate(R.id.action_DashboardFragment_to_voucherListFragment)
        }
        binding.mapCard.setOnClickListener {
            findNavController().navigate(R.id.action_DashboardFragment_to_mapsFragment)
        }
        binding.fundCollectionCard.setOnClickListener {
            findNavController().navigate(R.id.action_DashboardFragment_to_fundCollectionFragment)
        }


    }

    override fun binObserver() {

        dashboardViewModel.dashboardInfoVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
            when (it) {

                is NetworkResult.Error -> {
                    sendToLoginPage()
                    Log.i("TAG1", "binObserver: ${it.data!!}")
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.show()

                }

                is NetworkResult.Success -> {
                    binding.mainLayout.show()
                    binding.totalMember.text = it.data?.totalMember.toString()
                    dues = it.data?.totalDues?.toDouble()
                    binding.totalDues.text = dues.toString()
                    binding.totalVouchers.text = it.data?.totalVoucher.toString()


                }


            }

        }
    }


    private fun sendToLoginPage() {
        tokenManager.saveToken(Constants.TOKEN, Constants.NO_DATA)
        startActivity(Intent(requireActivity(), LogInActivity::class.java))
        requireActivity().finish()


    }


}