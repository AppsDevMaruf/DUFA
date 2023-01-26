package com.marufalam.dufa.ui

import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.databinding.FragmentPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentPaymentBinding>() {


    override fun getFragmentView(): Int {
        return R.layout.fragment_payment
    }

    override fun configUi() {
        binding.registerCard.setOnClickListener {

        }
//        binding.poneTimeFee.setOnClickListener {
//            sslSetUp(3000)
//        }
//        binding.yearlePaymentsCard.setOnClickListener {
//            sslSetUp(5000)
//        }

    }

    override fun setupNavigation() {

    }

    override fun binObserver() {

    }


}
