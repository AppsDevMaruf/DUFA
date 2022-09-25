package com.marufalam.dufa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.marufalam.dufa.adapter.MemberListAdapter
import com.marufalam.dufa.databinding.FragmentMemberListBinding
import com.marufalam.dufa.viewmodel.DashboardViewModel

class MemberListFragment : Fragment() {
    private lateinit var binding: FragmentMemberListBinding
    val dashboardViewModel :DashboardViewModel by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(requireActivity(),"WealCome",Toast.LENGTH_LONG)
        // Inflate the layout for this fragment
        binding = FragmentMemberListBinding.inflate(inflater,container,false)
        Toast.makeText(requireActivity(),"WealCome",Toast.LENGTH_LONG)
        val adapter = MemberListAdapter()
        val llm = LinearLayoutManager(requireActivity())
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.memberListRv.layoutManager = llm
        binding.memberListRv.adapter = adapter
        Toast.makeText(requireActivity(),"WealCome",Toast.LENGTH_LONG)
        dashboardViewModel.fetchData()
        dashboardViewModel.memberlistLD.observe(viewLifecycleOwner){
            adapter.submitList(it.users)
            Toast.makeText(requireActivity(),"${it.users}",Toast.LENGTH_LONG)
            Log.e("TAG", "onCreateViewDashboard: $it")
        }
        return binding.root
    }

}