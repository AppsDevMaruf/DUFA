package com.marufalam.dufa.ui

import android.annotation.SuppressLint
import android.text.Html.fromHtml
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.marufalam.dufa.R
import com.marufalam.dufa.adapter.MemberListAdapter
import com.marufalam.dufa.databinding.FragmentMemberListBinding
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.utils.Constants.TAG
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.hide
import com.marufalam.dufa.utils.show
import com.marufalam.dufa.utils.showAlert
import com.marufalam.dufa.viewmodel.DashboardViewModel


class MemberListFragment : BaseFragment<FragmentMemberListBinding>() {
    private val adapter = MemberListAdapter()
    private val dashboardViewModel: DashboardViewModel by activityViewModels()


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




    override fun getFragmentView(): Int {
        return R.layout.fragment_member_list

    }

    override fun configUi() {


        val llm = LinearLayoutManager(requireActivity())
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.memberListRv.layoutManager = llm
        binding.memberListRv.adapter = adapter
        Toast.makeText(requireActivity(), "Welcome", Toast.LENGTH_LONG).show()
        dashboardViewModel.getMemberList()

    }

    override fun setupNavigation() {

    }

    override fun binObserver() {
        dashboardViewModel.getMemberListResponse.observe(viewLifecycleOwner) {
            binding.progress.hide()
            when (it) {


                is NetworkResult.Error -> {
                    showAlert(requireActivity(), it.message!!)
                }
                is NetworkResult.Loading -> {
                    binding.mainLayout.hide()
                    binding.progress.show()
                }
                is NetworkResult.Success -> {
                    binding.mainLayout.show()
                    adapter.submitList(it.data?.users)
                    Log.i(TAG, "binObserver:${it.data}")


                    }


                }

            }

        }

    }

