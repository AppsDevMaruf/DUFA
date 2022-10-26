package com.marufalam.dufa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.databinding.FragmentDashboardBinding
import com.marufalam.dufa.fragments.BaseFragment


class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {




    override fun getFragmentView(): Int {
       return R.layout.fragment_dashboard
    }

    override fun configUi() {
        binding.memberListCard.setOnClickListener{
            findNavController().navigate(R.id.action_DashboardFragment_to_memberListFragment)

        }

    }

    override fun setupNavigation() {

    }

    override fun binObserver() {

    }

}