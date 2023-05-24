package com.marufalam.dufa.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
open class MemberListFragment :
    BaseFragment<FragmentMemberListBinding>(), MemberSelectListener, SearchByListener {
    var bundle = Bundle()
    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    private lateinit var searchAdapter: SearchMemberListAdapter

    lateinit var requestSearch: RequestSearch
    lateinit var searchItemAdapter: SearchItemAdapter

    var type: String = ""

    var itemsBy: MutableList<SearchBy> = mutableListOf()

    lateinit var bottomSheetDialogSearchItem: BottomSheetDialog
    private var timer: Timer = Timer()
    private val DELAY: Long = 1000 // in ms
    private var hasData = false

    override fun getFragmentView(): Int {
        return R.layout.fragment_member_list
    }

    override fun onResume() {
        super.onResume()
        itemsBy.clear()
        bottomSheetDialogSearchItem.dismiss()
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun configUi() {

        //        val onBackPressedCallback = object : OnBackPressedCallback(true) {
        //            override fun handleOnBackPressed() {
        //                requireActivity().supportFragmentManager.beginTransaction()
        //                    .remove(this@MemberListFragment).commit()
        //               // requireActivity().supportFragmentManager.popBackStack()
        //            }
        //        }
        //        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        bottomSheetDialogSearchItem = BottomSheetDialog(requireContext())
        searchAdapter = SearchMemberListAdapter(this)
        searchItemAdapter = SearchItemAdapter(this, type)
        binding.memberListRv.adapter = searchAdapter

        searchBYSelectedItem(SearchBy(null, null))

        binding.searchET.onTextChanged { it ->
            if (it.length >= 3) {
                timer = Timer()
                timer.schedule(
                    object : TimerTask() {
                        override fun run() {
                            requestSearch = RequestSearch(null, null, null, null, null, it, 0)

                            dashboardViewModel.getMemberSearchVMLD(requestSearch)
                            GlobalScope.launch(Dispatchers.Main) {
                                dashboardViewModel.getMemberSearchVMLD(requestSearch).observe(
                                    viewLifecycleOwner
                                ) {
                                    searchAdapter.submitData(lifecycle, it)
                                }
                            }
                        }
                    },
                    DELAY
                )
            }
        }
    }

    private fun showBottomSheetFilterType() {
        binding.searchET.gone()
        binding.dobTV.gone()
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.item_filter)
        bottomSheetDialog.behavior.maxHeight = 2000 // set max height when expanded in PIXEL
        bottomSheetDialog.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        )
        bottomSheetDialog.findViewById<LinearLayout>(R.id.nameOrEmailBtn)!!.setOnClickListener {
            binding.titleText.text = it.tag.toString()
            binding.filterTypeIcon.setImageResource(R.drawable.email)
            binding.titleText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            binding.searchET.show()
            type = it.tag.toString()
            hasData = false
            searchItemAdapter = SearchItemAdapter(this, type)

            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.findViewById<LinearLayout>(R.id.bloodGroupBtn)!!.setOnClickListener {
            binding.titleText.text = it.tag.toString()
            binding.filterTypeIcon.setImageResource(R.drawable.blood)
            binding.titleText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            type = it.tag.toString()
            dashboardViewModel.getBloodGroupVM()
            hasData = false
            searchItemAdapter = SearchItemAdapter(this, type)

            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.findViewById<LinearLayout>(R.id.districtBtn)!!.setOnClickListener {
            binding.titleText.text = it.tag.toString()
            binding.filterTypeIcon.setImageResource(R.drawable.pin)
            binding.titleText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            type = it.tag.toString()
            dashboardViewModel.districtVM()
            hasData = false
            searchItemAdapter = SearchItemAdapter(this, type)

            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.findViewById<LinearLayout>(R.id.occupationBtn)!!.setOnClickListener {
            binding.titleText.text = it.tag.toString()
            binding.filterTypeIcon.setImageResource(R.drawable.briefcase)
            binding.titleText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            type = it.tag.toString()
            dashboardViewModel.occupationsVM()
            hasData = false
            searchItemAdapter = SearchItemAdapter(this, type)

            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.findViewById<LinearLayout>(R.id.departmentBtn)!!.setOnClickListener {
            binding.titleText.text = it.tag.toString()
            binding.filterTypeIcon.setImageResource(R.drawable.department)
            binding.titleText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            type = it.tag.toString()
            dashboardViewModel.getDepartmentsVM()
            hasData = false
            searchItemAdapter = SearchItemAdapter(this, type)

            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.findViewById<LinearLayout>(R.id.dobBtn)!!.setOnClickListener {
            binding.titleText.text = it.tag.toString()
            binding.filterTypeIcon.setImageResource(R.drawable.birthday)
            binding.titleText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            type = it.tag.toString()
            binding.dobTV.show()
            hasData = false
            searchItemAdapter = SearchItemAdapter(this, type)

            bottomSheetDialog.dismiss()
        }



        bottomSheetDialog.show()
    }

    private fun showBottomSheetFilterItemType() {

        bottomSheetDialogSearchItem.setContentView(R.layout.item_filter_type)
        bottomSheetDialogSearchItem.behavior.maxHeight =
            2000 // set max height when expanded in PIXEL
        bottomSheetDialogSearchItem.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        )


        val itemRcv: RecyclerView =
            bottomSheetDialogSearchItem.findViewById<RecyclerView>(R.id.itemKeywordRCV)!!
        itemRcv.adapter = searchItemAdapter

        bottomSheetDialogSearchItem.show()
    }

    override fun setupNavigation() {
        binding.filterTypeSpinner.setOnClickListener {
            showBottomSheetFilterType()
            hideSoftKeyboard()
        }
        binding.dobTV.setOnClickListener {
            datePickerFun {
                binding.dobTV.text = it

                requestSearch = RequestSearch(it, null, null, null, null, null, 0)
                Log.i("TAG", "requestSearch: $requestSearch")
                dashboardViewModel.getMemberSearchVMLD(requestSearch).observe(viewLifecycleOwner) {
                    searchAdapter.submitData(lifecycle, it)
                }
                hideSoftKeyboard()
            }
        }
    }

    override fun binObserver() {

        dashboardViewModel.getDepartmentsVMLD.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    itemsBy.clear()

                    it.data?.departments?.forEach { departments ->
                        val searchBy: SearchBy = SearchBy("", departments?.name.toString())

                        itemsBy.add(searchBy)
                    }

                    Log.i("TAG", "departments: $itemsBy ")

                    searchItemAdapter.submitList(itemsBy)
                    hasData = true

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

                    it.data?.bloodgroups?.forEach { bloodGroup ->
                        val searchBy: SearchBy = SearchBy("", bloodGroup?.name.toString())

                        itemsBy.add(searchBy)
                    }

                    searchItemAdapter.submitList(itemsBy)
                    hasData = true
                    Log.i("TAG", "departments: $itemsBy ")
                    showBottomSheetFilterItemType()
                }
            }
        }
        dashboardViewModel.districtVMLD.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    itemsBy.clear()

                    it.data?.districts?.forEach { districts ->
                        val searchBy: SearchBy = SearchBy("", districts.name)

                        itemsBy.add(searchBy)
                    }

                    searchItemAdapter.submitList(itemsBy)
                    hasData = true
                    Log.i("TAG", "departments: $itemsBy ")
                    showBottomSheetFilterItemType()
                }
            }
        }
        dashboardViewModel.occupationsVMLD.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    itemsBy.clear()

                    it.data?.occupations?.forEach { occupations ->
                        val searchBy: SearchBy = SearchBy("", occupations.name)

                        itemsBy.add(searchBy)
                    }

                    searchItemAdapter.submitList(itemsBy)
                    hasData = true
                    Log.i("TAG", "departments: $itemsBy ")
                    showBottomSheetFilterItemType()
                }
            }
        }
    }

    override fun selectedMember(responseDetail: Data?) {
        bundle.putParcelable("memberInfo", responseDetail) // Key, value
        findNavController().navigate(R.id.action_memberListFragment_to_userDetailsFragment, bundle)
    }

    @SuppressLint("SetTextI18n")
    override fun searchBYSelectedItem(searchBy: SearchBy) {

        Log.i("TAG", "searchBYSelectedItem: $searchBy ")

        bottomSheetDialogSearchItem.dismiss()

        binding.titleText.text = "${binding.titleText.text}  ${searchBy.name ?: ""}"

        searchItemAdapter = SearchItemAdapter(this, type)
        Log.i("type", "type: $type")


        val requestSearch =
            when (type) {
                "Blood Group" -> {
                    RequestSearch(null, searchBy.name, null, null, null, null, 0)
                }
                "District" -> {
                    RequestSearch(null, null, null, searchBy.name, null, null, 0)
                }
                "Occupation" -> {
                    RequestSearch(null, null, null, null, searchBy.name, null, 0)
                }
                "Department" -> {
                    RequestSearch(null, null, searchBy.name, null, null, null, 0)
                }
                else -> {
                    RequestSearch(null, null, null, null, null, null, 0)
                }
            }

        dashboardViewModel.getMemberSearchVMLD(requestSearch).observe(viewLifecycleOwner) {
            searchAdapter.submitData(lifecycle, it)
        }
    }
}
