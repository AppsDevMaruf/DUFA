package org.dufa.dufa9596.ui

import org.dufa.dufa9596.BaseFragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.databinding.FragmentPaymentBinding
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
