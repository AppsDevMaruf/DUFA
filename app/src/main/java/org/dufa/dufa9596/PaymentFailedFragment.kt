package org.dufa.dufa9596

import androidx.navigation.fragment.findNavController
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