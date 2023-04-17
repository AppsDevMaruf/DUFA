package com.marufalam.dufa.ui.dues_payment

import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.databinding.FragmentDuesPaymentBinding

class DuesPaymentFragment : BaseFragment<FragmentDuesPaymentBinding>(){



    override fun getFragmentView(): Int {
        return R.layout.fragment_dues_payment
    }


    override fun binObserver() {

        binding.paymentHistory.setOnClickListener {

            findNavController().navigate(R.id.action_duesPaymentFragment_to_transactionHistoryFragment)

        }

    }


}