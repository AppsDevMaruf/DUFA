package org.dufa.dufa9596

import androidx.navigation.fragment.findNavController
import org.dufa.dufa9596.databinding.FragmentPayentSuccessBinding


class PaymentSuccessFragment : BaseFragment<FragmentPayentSuccessBinding>() {



    override fun getFragmentView(): Int {

        return R.layout.fragment_payent_success
    }

    override fun setupNavigation() {

        findNavController().navigate(R.id.DashboardFragment)


    }


}