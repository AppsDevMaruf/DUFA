package com.marufalam.dufa.fragments.memberList

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html.fromHtml
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.marufalam.dufa.R
import com.marufalam.dufa.adapter.MemberListAdapter
import com.marufalam.dufa.databinding.FragmentMemberListBinding
import com.marufalam.dufa.viewmodel.DashboardViewModel


class MemberListFragment : Fragment() {
    private lateinit var binding: FragmentMemberListBinding
    val dashboardViewModel: DashboardViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchView = menu.findItem(R.id.member_search).actionView as SearchView

        searchView.queryHint = fromHtml("<font color = #ffffff>" + getResources().getString(R.string.search_hint) + "</font>");
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(requireActivity(), "WealCome", Toast.LENGTH_LONG).show()
        // Inflate the layout for this fragment
        binding = FragmentMemberListBinding.inflate(inflater, container, false)
        Toast.makeText(requireActivity(), "WealCome", Toast.LENGTH_LONG)
        val adapter = MemberListAdapter()
        val llm = LinearLayoutManager(requireActivity())
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.memberListRv.layoutManager = llm
        binding.memberListRv.adapter = adapter
        Toast.makeText(requireActivity(), "WealCome", Toast.LENGTH_LONG)
        dashboardViewModel.fetchData()
        dashboardViewModel.memberlistLD.observe(viewLifecycleOwner) {
            adapter.submitList(it.users)
            Toast.makeText(requireActivity(), "${it.users}", Toast.LENGTH_LONG)
            Log.e("TAG", "onCreateViewDashboard: $it")
        }
        return binding.root
    }

}