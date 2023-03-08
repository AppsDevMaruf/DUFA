package com.marufalam.dufa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marufalam.dufa.data.models.search.Data
import com.marufalam.dufa.databinding.FragmentUserDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>() {


    override fun getFragmentView(): Int {
        return R.layout.fragment_user_details
    }

    override fun configUi() {
        if (arguments!=null){
            val userInfo: Data = requireArguments().getParcelable("memberInfo")!!
            binding.name.text = userInfo.name
            binding.address.text = userInfo.address
            binding.bloodGroup.text = userInfo.bloodgroup
            binding.gender.text = userInfo.gender
        }

    }




}