package com.marufalam.dufa.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.adapter.MemberListAdapter
import com.marufalam.dufa.adapter.SearchMemberListAdapter
import com.marufalam.dufa.data.models.dashboard.Allmember
import com.marufalam.dufa.databinding.FragmentMemberListBinding
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.viewmodel.DashboardViewModel
import java.util.*


class MemberListFragment : BaseFragment<FragmentMemberListBinding>() {

    private var selectedCategory: String? = null

    var searchByList = mutableListOf<String>()


    lateinit var adapter: MemberListAdapter
    private val dashboardViewModel: DashboardViewModel by activityViewModels()


    var userDataList = mutableListOf<Allmember>()

    private var totalPage = 1
    private lateinit var searchAdapter: SearchMemberListAdapter


    override fun getFragmentView(): Int {
        return R.layout.fragment_member_list

    }

    override fun configUi() {
        searchAdapter= SearchMemberListAdapter()
        binding.memberListRv.adapter = searchAdapter

        dashboardViewModel.getMemberSearchVMLD(totalPage, "")
            .observe(viewLifecycleOwner) {
                searchAdapter.submitData(lifecycle, it)

            }


        val filterByAdapter: ArrayAdapter<*>


        val showList = resources.getStringArray(R.array.filterBy)
        var searchDataList = mutableListOf<String>()


        filterByAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, showList)

        binding.filterbySpiner.adapter = filterByAdapter

        binding.filterbySpiner.onItemSelectedListener =
            object : OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?, p1: View?, position: Int, p3: Long
                ) {
                    val itemAdapter: ArrayAdapter<*>
                    searchByList = resources.getStringArray(R.array.filterBy).toMutableList()
                    selectedCategory = searchByList[position]
                    binding.searchLayout.visibility = View.GONE
                    binding.itemSpiner.visibility = View.VISIBLE
                    when (selectedCategory) {

                        "Department" -> {
                            itemAdapter = ArrayAdapter(
                                requireActivity(),
                                android.R.layout.simple_list_item_1,
                                resources.getStringArray(R.array.department)

                            )


                            searchDataList =
                                resources.getStringArray(R.array.department).toMutableList()

                            binding.itemSpiner.adapter = itemAdapter

                        }
                        "Blood Group" -> {
                            itemAdapter = ArrayAdapter(
                                requireActivity(),
                                android.R.layout.simple_list_item_1,
                                resources.getStringArray(R.array.bloodgroup)
                            )
                            searchDataList =
                                resources.getStringArray(R.array.bloodgroup).toMutableList()

                            binding.itemSpiner.adapter = itemAdapter
                        }
                        "Occupation" -> {
                            itemAdapter = ArrayAdapter(
                                requireActivity(),
                                android.R.layout.simple_list_item_1,
                                resources.getStringArray(R.array.profession)
                            )

                            searchDataList =
                                resources.getStringArray(R.array.profession).toMutableList()
                            binding.itemSpiner.adapter = itemAdapter
                        }
                        "District" -> {
                            itemAdapter = ArrayAdapter(
                                requireActivity(),
                                android.R.layout.simple_list_item_1,
                                resources.getStringArray(R.array.district)
                            )

                            searchDataList =
                                resources.getStringArray(R.array.district).toMutableList()
                            binding.itemSpiner.adapter = itemAdapter
                        }
                        "Name Or Email" -> {

                            binding.searchLayout.visibility = View.VISIBLE

                        }


                    }


                }

                override fun onNothingSelected(parent: AdapterView<*>?) {


                }
            }


    }


    override fun setupNavigation() {


    }

    override fun binObserver() {


    }

}



