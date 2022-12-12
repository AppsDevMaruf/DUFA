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
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.utils.Constants.TAG
import com.marufalam.dufa.viewmodel.DashboardViewModel
import java.util.Locale


class MemberListFragment : BaseFragment<FragmentMemberListBinding>() {
    private val adapter = MemberListAdapter()
    private val dashboardViewModel: DashboardViewModel by activityViewModels()
    var filterBy = ""
    var searchParam = ""
    var listBy = ""


    override fun getFragmentView(): Int {
        return R.layout.fragment_member_list

    }

    override fun configUi() {

        val filterByAdapter: ArrayAdapter<*>

        val showList = arrayOf("Filter By ", "Blood Group", "Department", "Profession", "District")
        val paramList = arrayOf("Filter By ", "bloodgroup", "department", "occupation", "district")

        val typeList = arrayOf(
            "Filter By ",
            Constants.BY_BLOOD,
            Constants.BY_DEPARTMENT,
            Constants.BY_PROFESSION,
            Constants.BY_DISTRICT
        )

        filterByAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, showList)

        binding.filterbySpiner.adapter = filterByAdapter

        binding.filterbySpiner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    posaition: Int,
                    p3: Long
                ) {
                    filterBy = showList[posaition]
                    searchParam = typeList[posaition]


                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }

        val llm = LinearLayoutManager(requireActivity())
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.memberListRv.layoutManager = llm
        binding.memberListRv.adapter = adapter

        dashboardViewModel.getMemberList()


        binding.searchBtn.setOnClickListener {

            val searchText = binding.searchKey.text.toString().trim()

            dashboardViewModel.getMemberListSearchVM(searchText, searchParam)


        }


//        val itemAdapter: ArrayAdapter<*>
//
//        val itemList = arrayOf("Select a Title", "PASSPORT", "NATIONAL ID", "DRIVING LICENSE")
//
//        itemAdapter =
//            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, itemList)
//
//        binding.itembySpiner.adapter = itemAdapter
//
//        binding.itembySpiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posaition: Int, p3: Long) {
//                listBy = itemList[posaition]
//
//
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//
//        }


    }

    private fun searchUser(searchText: CharSequence) {


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

