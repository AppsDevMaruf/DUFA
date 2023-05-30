package com.marufalam.dufa.ui

import android.content.Intent
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.data.local.TokenManager
import com.marufalam.dufa.databinding.FragmentDashboardBinding
import com.marufalam.dufa.utils.Constants
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.gone
import com.marufalam.dufa.utils.hide
import com.marufalam.dufa.utils.show
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val dashboardViewModel by activityViewModels<DashboardViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager
    override fun getFragmentView(): Int {
        return R.layout.fragment_dashboard
    }

    override fun configUi() {

        binding.progressBar.show()
        binding.mainLayout.gone()
        dashboardViewModel.profileInfoVM()
        dashboardViewModel.dashboardInfoVM()


    }

    override fun setupNavigation() {
        binding.memberListCard.setOnClickListener {
            findNavController().navigate(R.id.action_DashboardFragment_to_memberListFragment)
        }
        binding.duesPaymentBtn.setOnClickListener {

            val dues = binding.totalDues.text.toString().toInt()
            if (dues > 0) {
                findNavController().navigate(R.id.action_DashboardFragment_to_duesPaymentFragment)
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
            binding.progressBar.hide()
            when (it) {

                is NetworkResult.Error -> {
                   // sendToLoginPage()
                    Log.i("TAG1", "binObserver: ${it.data!!.toString()}")
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.show()

                }

                is NetworkResult.Success -> {

                    binding.progressBar.gone()
                    binding.mainLayout.show()

                    it.data?.totalDues?.let { it1 -> dashboardViewModel.savePaymentDues(it1) }
                    binding.totalMember.text = it.data?.totalMember.toString()
                    binding.totalDues.text = it.data?.totalDues.toString()
                    binding.totalVouchers.text = it.data?.totalVoucher.toString()


                }


            }

        }
    }


    fun sendToLoginPage() {
        tokenManager.saveToken(Constants.TOKEN, Constants.NO_DATA)

        startActivity(Intent(requireActivity(), LogInActivity::class.java))
        requireActivity().finish()


    }


}