package org.dufa.dufa9596.ui

import androidx.navigation.fragment.findNavController
import org.dufa.dufa9596.BaseFragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.databinding.FragmentPaymentFailledBinding

class PaymentFailedFragment : BaseFragment<FragmentPaymentFailledBinding>() {


    override fun getFragmentView(): Int {
        return R.layout.fragment_payment_failled
    }

    override fun setupNavigation() {

        binding.goBackBtn.setOnClickListener {
            findNavController().navigate(R.id.DashboardFragment)


        }


    }

}