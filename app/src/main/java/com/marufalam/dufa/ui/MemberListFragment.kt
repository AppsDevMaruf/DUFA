package com.marufalam.dufa.ui

import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.adapter.MemberListAdapter
import com.marufalam.dufa.databinding.FragmentMemberListBinding
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.viewmodel.DashboardViewModel
import kotlinx.coroutines.NonDisposableHandle.parent


class MemberListFragment : BaseFragment<FragmentMemberListBinding>() {
    private val selectedItem: String? = null
    private var selectedCategory :String? = null
    private val tvStateSpinner: TextView? =null
    private  var tvDistrictSpinner :TextView? = null
    private val  filterbySpiner: Spinner? = null
    private  var itemSpiner :Spinner? = null


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


        val showList = resources.getStringArray(R.array.filterBy)
        val paramList = arrayOf("Filter By ", "bloodgroup", "department", "occupation", "district")

        val typeList = arrayOf(
            "Filter By ",
            Constants.BY_BLOOD,
            Constants.BY_DEPARTMENT,
            Constants.BY_PROFESSION,
            Constants.BY_DISTRICT
        )

        filterByAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1,showList)

        binding.filterbySpiner.adapter = filterByAdapter

        binding.filterbySpiner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?, p1: View?, posaition: Int, p3: Long
                ) {
                    filterBy = showList[posaition]
                    searchParam = typeList[posaition]
                    var itemAdapter: ArrayAdapter<*>
                    selectedCategory = filterbySpiner?.selectedItem.toString();
                    val parentID: Int = p0!!.id
                    if (parentID == binding.filterbySpiner.id){
                        when (selectedCategory){
                             "Select Your State"->{
                                 itemAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1,resources.getStringArray(R.array.bloodgroup))

                             }
                            "Blood Group"->{
                                itemAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1,resources.getStringArray(R.array.bloodgroup))
                            }



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

