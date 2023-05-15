package com.marufalam.dufa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.databinding.FragmentPayentSuccessBinding
import com.marufalam.dufa.databinding.FragmentPaymentFailledBinding

class PaymentFailledFragment : BaseFragment<FragmentPaymentFailledBinding>() {


    override fun getFragmentView(): Int {
        return R.layout.fragment_payment_failled
    }

    override fun setupNavigation() {

        binding.goBackBtn.setOnClickListener {
            findNavController().navigate(R.id.DashboardFragment)


        }


    }

}