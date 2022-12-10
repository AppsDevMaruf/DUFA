package com.marufalam.dufa.ui

import android.annotation.SuppressLint
import android.text.Html.fromHtml
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import java.util.Locale


class MemberListFragment : BaseFragment<FragmentMemberListBinding>() {
    private val adapter = MemberListAdapter()
    private val dashboardViewModel: DashboardViewModel by activityViewModels()
    var filterBy = ""
    var listBy = ""


    @SuppressLint("ResourceAsColor")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchView = menu.findItem(R.id.member_search).actionView as SearchView

        searchView.queryHint =
            fromHtml("<font color = #ffffff>" + resources.getString(R.string.search_hint) + "</font>");
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

        val filterbyAdapter: ArrayAdapter<*>

        val showList = arrayOf("Filter By ", "Blood Group", "Department", "Profession","District")

        filterbyAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, showList)

        binding.filterbySpiner.adapter = filterbyAdapter

        binding.filterbySpiner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    posaition: Int,
                    p3: Long
                ) {
                    filterBy = showList[posaition]


                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }

        val itemAdapter: ArrayAdapter<*>

        val itemList = arrayOf("Select a Title", "PASSPORT", "NATIONAL ID", "DRIVING LICENSE")

        itemAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, itemList)

        binding.itembySpiner.adapter = itemAdapter

        binding.itembySpiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posaition: Int, p3: Long) {
                listBy = itemList[posaition]


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        val llm = LinearLayoutManager(requireActivity())
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.memberListRv.layoutManager = llm
        binding.memberListRv.adapter = adapter
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


                }


            }

        }

    }

}

