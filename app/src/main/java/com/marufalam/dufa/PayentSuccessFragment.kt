package com.marufalam.dufa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.databinding.FragmentPayentSuccessBinding


class PayentSuccessFragment : BaseFragment<FragmentPayentSuccessBinding>() {



    override fun getFragmentView(): Int {

        return R.layout.fragment_payent_success
    }

    override fun setupNavigation() {

        findNavController().navigate(R.id.DashboardFragment)


    }


}