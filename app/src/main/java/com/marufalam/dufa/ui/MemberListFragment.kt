package com.marufalam.dufa.ui

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.adapter.SearchMemberListAdapter
import com.marufalam.dufa.data.models.SearchBy
import com.marufalam.dufa.data.models.search.Data
import com.marufalam.dufa.data.models.search.RequestSearch
import com.marufalam.dufa.databinding.FragmentMemberListBinding
import com.marufalam.dufa.interfaces.MemberSelectListener
import com.marufalam.dufa.interfaces.SearchByListener
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.hideSoftKeyboard
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
open class MemberListFragment : BaseFragment<FragmentMemberListBinding>(), MemberSelectListener,
    SearchByListener {
    var bundle = Bundle()
    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    private lateinit var searchAdapter: SearchMemberListAdapter

    lateinit var requestSearch: RequestSearch
    lateinit var searchItemAdapter: SearchItemAdapter

    var type: String = ""

    var itemsBy: MutableList<SearchBy> = mutableListOf()

    lateinit var bottomSheetDialogSearchItem: BottomSheetDialog


    override fun getFragmentView(): Int {
        return R.layout.fragment_member_list

    }

    override fun configUi() {
        searchAdapter = SearchMemberListAdapter(this)
        searchItemAdapter = SearchItemAdapter(this)
        binding.memberListRv.adapter = searchAdapter
        //searchItemAdapter = SearchItemAdapter(this)


        binding.filterTypeSpinner.setOnClickListener {
            showBottomSheetFilterType()
            hideSoftKeyboard()
        }


    }

    private fun showBottomSheetFilterType() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.item_filter)
        bottomSheetDialog.behavior.maxHeight = 2000 // set max height when expanded in PIXEL
        bottomSheetDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)


        bottomSheetDialog.findViewById<LinearLayout>(R.id.nameOrEmailBtn)!!.setOnClickListener {
            binding.titleText.text = it.tag.toString()
            binding.titleText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))

            type = it.tag.toString()

            bottomSheetDialog.dismiss()


        }

        bottomSheetDialog.findViewById<LinearLayout>(R.id.bloodGroupBtn)!!.setOnClickListener {
            binding.titleText.text = it.tag.toString()
            binding.titleText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            type = it.tag.toString()

            dashboardViewModel.getBloodGroupVM()



            bottomSheetDialog.dismiss()

        }
        bottomSheetDialog.findViewById<LinearLayout>(R.id.districtBtn)!!.setOnClickListener {

            binding.titleText.text = it.tag.toString()
            binding.titleText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))


            type = it.tag.toString()

            bottomSheetDialog.dismiss()

        }
        bottomSheetDialog.findViewById<LinearLayout>(R.id.occupationBtn)!!.setOnClickListener {
            binding.titleText.text = it.tag.toString()
            binding.titleText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))


            type = it.tag.toString()

            bottomSheetDialog.dismiss()

        }
        bottomSheetDialog.findViewById<LinearLayout>(R.id.departmentBtn)!!.setOnClickListener {
            binding.titleText.text = it.tag.toString()
            binding.titleText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))

            type = it.tag.toString()
            dashboardViewModel.getDepartmentsVM()

            bottomSheetDialog.dismiss()

        }
        bottomSheetDialog.findViewById<LinearLayout>(R.id.dobBtn)!!.setOnClickListener {
            binding.titleText.text = it.tag.toString()
            binding.titleText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            type = it.tag.toString()
            showBottomSheetState()

            bottomSheetDialog.dismiss()

        }
        bottomSheetDialog.show()
    }


    private fun showBottomSheetFilterItemType() {


        bottomSheetDialogSearchItem = BottomSheetDialog(requireContext())
        bottomSheetDialogSearchItem.setContentView(R.layout.item_filter_type)
        bottomSheetDialogSearchItem.behavior.maxHeight =
            2000 // set max height when expanded in PIXEL
        bottomSheetDialogSearchItem.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)


        var itemRcv: RecyclerView =
            bottomSheetDialogSearchItem.findViewById<RecyclerView>(R.id.itemKeywordRCV)!!
        itemRcv.adapter = searchItemAdapter

        bottomSheetDialogSearchItem.show()
    }


    override fun setupNavigation() {


    }

    override fun binObserver() {

        dashboardViewModel.getDepartmentsVMLD.observe(viewLifecycleOwner) {

            when (it) {
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}

                is NetworkResult.Success -> {
                    itemsBy.clear()

                    it.data?.departments?.forEach {
                        val searchBy: SearchBy = SearchBy("", it?.name.toString())

                        itemsBy.add(searchBy)


                    }

                    Log.i("TAG", "departments: $itemsBy ")

                    searchItemAdapter.submitList(itemsBy)


                    showBottomSheetFilterItemType()


                }
            }

        }

        dashboardViewModel.getBloodGroupVMLD.observe(viewLifecycleOwner) {

            when (it) {
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    itemsBy.clear()

                    it.data?.bloodgroups?.forEach {
                        val searchBy: SearchBy = SearchBy("", it?.name.toString())

                        itemsBy.add(searchBy)


                    }

                    searchItemAdapter.submitList(itemsBy)
                    Log.i("TAG", "departments: $itemsBy ")
                    showBottomSheetFilterItemType()


                }
            }

        }


    }

    private fun showBottomSheetState() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.item_bottom_search)
        bottomSheetDialog.behavior.maxHeight = 1000 // set max height when expanded in PIXEL
        bottomSheetDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        // bottomSheetDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        bottomSheetDialog.findViewById<ImageView>(R.id.cancel_buttonSheet)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }


        //  bottomSheetDialog.behavior.peekHeight = 400 // set default height when collapsed in PIXEL
        // val copy = bottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)
        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.searchItemRcv)


        buildSearchItemRecyclerView(recyclerView!!)
        //  val searchView = bottomSheetDialog.findViewById<SearchView>(R.id.searchText)


//        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                // inside on query text change method we are
//                // calling a method to filter our recycler view.
//                filterState(newText)
//                return false
//            }
//        })


        bottomSheetDialog.show()
    }

    private fun buildSearchItemRecyclerView(recyclerView: RecyclerView) {


        val manager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        // setting layout manager
        // to our recycler view.
        recyclerView.layoutManager = manager

        // setting adapter to
        // our recycler view.
        recyclerView.adapter = searchItemAdapter


    }

    override fun selectedMember(responseDetail: Data?) {
        bundle.putParcelable("memberInfo", responseDetail)  // Key, value
        findNavController().navigate(
            R.id.action_memberListFragment_to_userDetailsFragment,
            bundle
        )
    }

    override fun searchBYSelectedItem(searchBy: SearchBy) {

        Log.i("TAG", "searchBYSelectedItem: $searchBy ")

        bottomSheetDialogSearchItem.dismiss()


        val requestSearch = when (type) {
            "NameorEmail" -> {
                RequestSearch(null, "A+", null, null, null, 0)


            }

            "BloodGroup" -> {
                RequestSearch(null, searchBy.name, null, null, null, 0)


            }

            "District" -> {
                RequestSearch(null, null, null, searchBy.name, null, 0)


            }

            "occupation" -> {
                RequestSearch(null, null, null, null, searchBy.name, 0)


            }

            "Department" -> {
                RequestSearch(null, null, searchBy.name, null, null, 0)


            }

            "DateofBirth" -> {
                RequestSearch(searchBy.name, null, null, null, null, 0)


            }


            else -> {
                RequestSearch(null, null, null, null, null, 0)
            }
        }



        dashboardViewModel.getMemberSearchVMLD(requestSearch)
            .observe(viewLifecycleOwner) {
                searchAdapter.submitData(lifecycle, it)

            }


    }


}



