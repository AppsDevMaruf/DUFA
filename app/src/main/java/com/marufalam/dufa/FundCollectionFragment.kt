package com.marufalam.dufa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marufalam.dufa.databinding.FragmentFundCollectionBinding

class FundCollectionFragment : BaseFragment<FragmentFundCollectionBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fund_collection, container, false)
    }

    override fun getFragmentView(): Int {

    }


}