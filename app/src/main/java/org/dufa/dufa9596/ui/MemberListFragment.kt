package org.dufa.dufa9596.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.dufa.dufa9596.BaseFragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.adapter.SearchMemberListAdapter
import org.dufa.dufa9596.data.models.SearchBy
import org.dufa.dufa9596.data.models.search.Data
import org.dufa.dufa9596.data.models.search.RequestSearch
import org.dufa.dufa9596.databinding.FragmentMemberListBinding
import org.dufa.dufa9596.interfaces.MemberSelectListener
import org.dufa.dufa9596.interfaces.SearchByListener
import org.dufa.dufa9596.paging.LoaderAdapter
import org.dufa.dufa9596.utils.*
import org.dufa.dufa9596.viewmodel.DashboardViewModel
import java.util.*

@AndroidEntryPoint
class MemberListFragment : BaseFragment<FragmentMemberListBinding>(), MemberSelectListener,
    SearchByListener {
    var bundle = Bundle()
    private val dashboardViewModel: DashboardViewModel by activityViewModels()
    private var filterType = ""

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

    override fun configUi() {

        bottomSheetDialogSearchItem = BottomSheetDialog(requireContext())
        searchAdapter = SearchMemberListAdapter(this)

        binding.memberListRv.adapter = searchAdapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()

        )

        CoroutineScope(Dispatchers.Main).launch {

            searchAdapter.loadStateFlow.collectLatest { loadStates ->

                if (loadStates.refresh is LoadState.Loading) {
                    binding.progressBar.show()
                } else {
                    binding.progressBar.gone()
                }
            }
        }



        searchItemAdapter = SearchItemAdapter(this, type)


        searchBYSelectedItem(SearchBy(null, null))

        binding.searchET.onTextChanged { it ->
            if (it.length >= 3) {
                timer = Timer()
                timer.schedule(
                    object : TimerTask() {
                        override fun run() {
                            requestSearch = RequestSearch(null, null, null, null, null, it, null, 0)
                            //dashboardViewModel.getMemberSearchVMLD(requestSearch)
                            CoroutineScope(Dispatchers.Main).launch {
                                dashboardViewModel.getMemberSearchVMLD(requestSearch) { dataFound ->
                                    if (!dataFound) {
                                        binding.memberListRv.gone()
                                        binding.noData.show()
                                    } else {
                                        binding.memberListRv.show()
                                        binding.noData.gone()
                                    }

                                }.collectLatest {

                                    searchAdapter.submitData(lifecycle, it)
                                }
                            }
                        }
                    },
                    DELAY
                )
            }
        }
        binding.clearFilter.setOnClickListener {
            searchBYSelectedItem(SearchBy(null, null))
            binding.titleText.text = "Select Filter Type:"
            binding.filterTypeIcon.setImageResource(R.drawable.ic_filter)
        }

    }


    private fun showBottomSheetFilterType() {
        binding.searchET.gone()
        binding.dobTV.gone()
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.item_filter)
        bottomSheetDialog.behavior.maxHeight = 2000 // set max height when expanded in PIXEL
        bottomSheetDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
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
            dashboardViewModel.bloodGroupVM()
            hasData = false
            searchItemAdapter = SearchItemAdapter(this, type)
            filterType = "Select a blood group"
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
            filterType = "Select a district"
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
            filterType = "Select an occupation"
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.findViewById<LinearLayout>(R.id.departmentBtn)!!.setOnClickListener {
            binding.titleText.text = it.tag.toString()
            binding.filterTypeIcon.setImageResource(R.drawable.department)
            binding.titleText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            type = it.tag.toString()
            dashboardViewModel.departmentVM()
            hasData = false
            searchItemAdapter = SearchItemAdapter(this, type)
            filterType = "Select a department"
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.findViewById<LinearLayout>(R.id.hallBtn)!!.setOnClickListener {
            binding.titleText.text = it.tag.toString()
            binding.filterTypeIcon.setImageResource(R.drawable.hall)
            binding.titleText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            type = it.tag.toString()
            dashboardViewModel.hallsVM()
            hasData = false
            searchItemAdapter = SearchItemAdapter(this, type)
            filterType = "Select a hall"
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
        val filterTitle = bottomSheetDialogSearchItem.findViewById<TextView>(R.id.filterTypeId)!!
        filterTitle.text = filterType
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
                requestSearch = RequestSearch(it, null, null, null, null, null, hall = null, 0)
                CoroutineScope(Dispatchers.Main).launch {

                    dashboardViewModel.getMemberSearchVMLD(requestSearch) { dataFound ->
                        if (!dataFound) {
                            binding.memberListRv.gone()
                            binding.noData.show()
                        } else {
                            binding.memberListRv.show()
                            binding.noData.gone()
                        }

                    }.collectLatest {
                        searchAdapter.submitData(lifecycle, it)
                    }
                }
                hideSoftKeyboard()
            }
        }
    }

    override fun binObserver() {
        dashboardViewModel.departmentVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Error -> {
                    showDialog(
                        context = requireActivity(),
                        title = "",
                        details = "${it.message}",
                        resId = R.drawable.ic_close,
                        yesContent = "Okay",
                        noContent = "Cancel",
                        showNoBtn = false,
                        positiveFun = {
                        }, {}
                    )

                }

                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }

                is NetworkResult.Success -> {
                    itemsBy.clear()

                    it.data?.departments?.sortedBy { department -> department?.name }
                        ?.forEach { departments ->
                            val searchBy = SearchBy("", departments?.name.toString())

                            itemsBy.add(searchBy)
                        }

                    searchItemAdapter.submitList(itemsBy)
                    hasData = true

                    showBottomSheetFilterItemType()
                }
            }
        }
        dashboardViewModel.bloodGroupVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Error -> {
                    showDialog(
                        context = requireActivity(),
                        title = "",
                        details = "${it.message}",
                        resId = R.drawable.ic_close,
                        yesContent = "Okay",
                        noContent = "Cancel",
                        showNoBtn = false,
                        positiveFun = {
                        }, {}
                    )
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }

                is NetworkResult.Success -> {
                    itemsBy.clear()

                    it.data?.bloodgroups?.sortedBy { bloodgroup -> bloodgroup?.name }
                        ?.forEach { bloodGroup ->
                            val searchBy = SearchBy("", bloodGroup?.name.toString())

                            itemsBy.add(searchBy)
                        }

                    searchItemAdapter.submitList(itemsBy)
                    hasData = true

                    showBottomSheetFilterItemType()
                }
            }
        }
        dashboardViewModel.districtVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Error -> {
                    showDialog(
                        context = requireActivity(),
                        title = "",
                        details = "${it.message}",
                        resId = R.drawable.ic_close,
                        yesContent = "Okay",
                        noContent = "Cancel",
                        showNoBtn = false,
                        positiveFun = {
                        }, {}
                    )
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }

                is NetworkResult.Success -> {
                    itemsBy.clear()
                    it.data?.districts?.sortedBy { district -> district.name }
                        ?.forEach { districts ->
                            val searchBy = SearchBy("", districts.name)

                            itemsBy.add(searchBy)
                        }

                    searchItemAdapter.submitList(itemsBy)
                    hasData = true

                    showBottomSheetFilterItemType()
                }
            }
        }
        dashboardViewModel.occupationsVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Error -> {
                    showDialog(
                        context = requireActivity(),
                        title = "",
                        details = "${it.message}",
                        resId = R.drawable.ic_close,
                        yesContent = "Okay",
                        noContent = "Cancel",
                        showNoBtn = false,
                        positiveFun = {
                        }, {}
                    )
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }

                is NetworkResult.Success -> {
                    itemsBy.clear()

                    it.data?.occupations?.sortedBy { occupation -> occupation.name }
                        ?.forEach { occupations ->
                            val searchBy = SearchBy("", occupations.name)

                            itemsBy.add(searchBy)
                        }

                    searchItemAdapter.submitList(itemsBy)
                    hasData = true

                    showBottomSheetFilterItemType()
                }
            }
        }
        dashboardViewModel.hallsVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Error -> {
                    showDialog(
                        context = requireActivity(),
                        title = "",
                        details = "${it.message}",
                        resId = R.drawable.ic_close,
                        yesContent = "Okay",
                        noContent = "Cancel",
                        showNoBtn = false,
                        positiveFun = {
                        }, {}
                    )
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }

                is NetworkResult.Success -> {
                    itemsBy.clear()

                    it.data?.halls?.sortedBy { hall -> hall?.name }?.forEach { occupations ->
                        val searchBy = SearchBy("", occupations?.name)
                        itemsBy.add(searchBy)
                    }

                    searchItemAdapter.submitList(itemsBy)
                    hasData = true
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


        bottomSheetDialogSearchItem.dismiss()

        binding.titleText.text = searchBy.name ?: "Select Filter Type"

        searchItemAdapter = SearchItemAdapter(this, type)


        val requestSearch =
            when (type) {
                "Blood Group" -> {
                    RequestSearch(null, searchBy.name, null, null, null, null, hall = null, 0)
                }

                "District" -> {
                    RequestSearch(null, null, null, searchBy.name, null, null, hall = null, 0)
                }

                "Occupation" -> {
                    RequestSearch(null, null, null, null, searchBy.name, null, hall = null, 0)
                }

                "Department" -> {
                    RequestSearch(null, null, searchBy.name, null, null, null, hall = null, 0)
                }

                "Hall" -> {
                    RequestSearch(null, null, null, null, null, null, hall = searchBy.name, 0)
                }

                else -> {
                    RequestSearch(null, null, null, null, null, null, hall = null, 0)
                }
            }

        CoroutineScope(Dispatchers.Main).launch {
            dashboardViewModel.getMemberSearchVMLD(requestSearch) { dataFound ->
                if (!dataFound) {
                    binding.memberListRv.gone()
                    binding.noData.show()
                } else {
                    binding.memberListRv.show()
                    binding.noData.gone()
                }

            }.collectLatest {
                searchAdapter.submitData(it)


            }


        }


    }


}
