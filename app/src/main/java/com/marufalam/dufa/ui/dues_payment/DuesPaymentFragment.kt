package com.marufalam.dufa.ui.dues_payment

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.databinding.FragmentDuesPaymentBinding
import com.marufalam.dufa.ui.trans_history.TransationHistoryViewModel
import com.marufalam.dufa.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DuesPaymentFragment : BaseFragment<FragmentDuesPaymentBinding>() {

    val registrationFee = 1000
    val yearlyFee = 2000
    val lifetimeFee = 10000


    private val viewModel by activityViewModels<TransationHistoryViewModel>()
    override fun getFragmentView(): Int {
        return R.layout.fragment_dues_payment
    }

    override fun binObserver() {
        viewModel.dueAmountVMLD.observe(viewLifecycleOwner){


          //  val dues = requireArguments().getInt("totalDues")
            val dues = it

            Log.i("TAG", "dues: $dues ")
            if (dues > 0) {

                binding.duesLayout.visibility = View.VISIBLE

            } else {
                binding.duesLayout.visibility = View.GONE
            }



        }



    }


    override fun configUi() {



    }

    override fun setupNavigation() {

        binding.paymentHistory.setOnClickListener {

            findNavController().navigate(R.id.action_duesPaymentFragment_to_transactionHistoryFragment)

        }
        binding.continue1000Payment.setOnClickListener {
            // todo registration fee
            toast("Working on it !")


        }

        binding.continue2000Payment.setOnClickListener {
            // todo  yearly fee
            toast("Working on it !")
        }

        binding.continue10000Payment.setOnClickListener {
            // todo lifetime payment

            toast("Working on it !")
        }


    }


}