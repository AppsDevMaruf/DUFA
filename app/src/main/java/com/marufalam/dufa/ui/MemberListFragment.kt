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


    override fun getFragmentView(): Int {
        return R.layout.fragment_member_list

    }

    override fun configUi() {


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

        binding.itemSpiner.visibility = View.GONE
        binding.itemSpiner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                val searchKeyWord = searchDataList[position]


                Toast.makeText(requireActivity(), "$searchKeyWord", Toast.LENGTH_LONG)
                    .show()
                filterUser(searchKeyWord)


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }


        dashboardViewModel.getAllMemberVM()

        binding.searchKey.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                var searchByName: String = s.toString()
                filterUser(searchByName)

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


//        binding.searchBtn.setOnClickListener {
//
//            val searchText = binding.searchKey.text.toString().trim()
//
//            dashboardViewModel.getMemberListSearchVM(searchText, searchParam)
//
//
//        }


    }

    private fun filterUser(text: String) {

        val filteredlist = ArrayList<Allmember>()
        for (item in userDataList) {
            // Log.i("TAG", "filterUser: ${item.bloodgroup} ")


            if (item.bloodgroup == text) {
                filteredlist.add(item)
            }

        }


        when (selectedCategory) {

            "Department" -> {

                for (item in userDataList) {
                    if (item.department == text) {
                        filteredlist.add(item)
                    }
                }


            }
            "Blood Group" -> {


            }
            "Occupation" -> {
                for (item in userDataList) {
                    if (item.occupation == text) {
                        filteredlist.add(item)
                    }
                }

            }
            "District" -> {
                for (item in userDataList) {
                    if (item.district == text) {
                        filteredlist.add(item)
                    }
                }

            }
            "Name Or Email" -> {

                binding.searchLayout.visibility = View.VISIBLE

                for (item in userDataList) {
                    if (item.name?.lowercase(Locale.ROOT)
                        !!.contains(text.lowercase(Locale.getDefault())) ||
                        item.email?.lowercase(Locale.ROOT)
                        !!.contains(text.lowercase(Locale.getDefault()))
                    ) {
                        filteredlist.add(item)
                    }
                }


            }


        }


        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
//            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist)
        }
    }


    private fun setRecycelcerView(userData: MutableList<Allmember>) {
        if (userData.isEmpty()) {
            binding.noData.visibility = View.VISIBLE
            binding.memberListRv.visibility = View.GONE
        } else {

            binding.noData.visibility = View.GONE
            binding.memberListRv.visibility = View.VISIBLE
            adapter = MemberListAdapter(requireActivity(), userData)
            val llm = LinearLayoutManager(requireActivity())
            llm.orientation = LinearLayoutManager.VERTICAL
            binding.memberListRv.layoutManager = llm
            binding.memberListRv.adapter = adapter
        }


    }

    override fun setupNavigation() {


    }

    override fun binObserver() {
        dashboardViewModel.getAllMemberVMLD.observe(viewLifecycleOwner) {
            binding.progress.hide()
            when (it) {


                is NetworkResult.Error -> {
                    showAlert(requireActivity(), it.message!!)
                }
                is NetworkResult.Loading -> {

                    binding.progress.show()
                }
                is NetworkResult.Success -> {


                    userDataList = it.data!!.allmembers.toMutableList()
                    println("................1.2........................ ${userDataList}")
                    setRecycelcerView(userDataList)

                }


            }

        }

    }

}



