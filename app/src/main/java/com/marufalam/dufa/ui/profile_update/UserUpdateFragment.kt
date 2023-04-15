package com.marufalam.dufa.ui.profile_update

import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.data.models.getProfileInfo.ResponseProfileInfo
import com.marufalam.dufa.data.models.get_departments.Department
import com.marufalam.dufa.data.models.get_districts.District
import com.marufalam.dufa.data.models.get_halls.Hall
import com.marufalam.dufa.data.models.get_occupations.Occupation
import com.marufalam.dufa.data.models.search.blood.Bloodgroup
import com.marufalam.dufa.databinding.FragmentUserUpdateBinding
import com.marufalam.dufa.interfaces.*
import com.marufalam.dufa.ui.profile_update.adapter.*
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class UserUpdateFragment : BaseFragment<FragmentUserUpdateBinding>(), DepartmentSelectListener,
    DistrictSelectListener, BloodGroupSelectListener, HallSelectListener, OccupationSelectListener {
    private val dashboardViewModel by viewModels<DashboardViewModel>()
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var departmentList: ArrayList<Department>
    private lateinit var districtList: ArrayList<District>
    private lateinit var hallList: ArrayList<Hall>
    private lateinit var bloodGroupList: ArrayList<Bloodgroup>
    private lateinit var occupationList: ArrayList<Occupation>

    private lateinit var departmentAdapter: DepartmentAdapter
    private lateinit var districtAdapter: DistrictAdapter
    private lateinit var bloodGroupAdapter: BloodGroupAdapter
    private lateinit var occupationAdapter: OccupationAdapter
    private lateinit var hallAdapter: HallAdapter
    private var title = "Male"

    override fun getFragmentView(): Int {
        return R.layout.fragment_user_update
    }


    override fun setupNavigation() {
        binding.departmentTypeSpinner.setOnClickListener {
            showBottomSheetDepartments()
            hideSoftKeyboard()
        }
        binding.districtTypeSpinner.setOnClickListener {
            showBottomSheetDistricts()
            hideSoftKeyboard()
        }
        binding.bloodGroupTypeSpinner.setOnClickListener {
            showBottomSheetBloodGroup()
            hideSoftKeyboard()
        }
        binding.occupationTypeSpinner.setOnClickListener {
            showBottomSheetOccupation()
            hideSoftKeyboard()
        }
        binding.hallTypeSpinner.setOnClickListener {
            showBottomSheetHall()
            hideSoftKeyboard()
        }
        binding.genderTypeSpinner.setOnClickListener {
            showBottomSheetGender()
            hideSoftKeyboard()
        }
        binding.birthdate.setOnClickListener {
            datePickerFun {
                binding.birthdate.text = it
            }
        }
    }

    private fun showBottomSheetGender() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.item_gender_type)
        bottomSheetDialog.behavior.maxHeight = 1000 // set max height when expanded in PIXEL
        bottomSheetDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)


        bottomSheetDialog.findViewById<LinearLayout>(R.id.btnMr)!!.setOnClickListener {

            title = "Male"
            binding.genderTypeSpinner
            binding.genderTypeText.text = title
            binding.genderTypeText.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.black
                )
            )
            binding.genderIcon.setImageResource(R.drawable.ic_mr)

            bottomSheetDialog.dismiss()


        }

        bottomSheetDialog.findViewById<LinearLayout>(R.id.btnMrs)!!.setOnClickListener {

            title = "Female"
            binding.genderTypeText.text = title
            binding.genderTypeText.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.black
                )
            )
            binding.genderIcon.setImageResource(R.drawable.ic_mrs)

            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    override fun configUi() {


        dashboardViewModel.occupationsVM()
        dashboardViewModel.districtVM()
        dashboardViewModel.hallsVM()
        dashboardViewModel.getBloodGroupVM()
        dashboardViewModel.getDepartmentsVM()

        if (arguments != null) {
            val userInfo: ResponseProfileInfo = requireArguments().getParcelable("userinfo")!!
            binding.name.setText(userInfo.name)
            binding.phone.setText(userInfo.phone)

            binding.address.setText(userInfo.address)
            binding.nid.setText(userInfo.nid)
            binding.genderTypeText.setText(userInfo.gender)
            binding.birthdate.text = userInfo.birthdate
            binding.departmentTypeText.setText(userInfo.department)
            binding.hallTypeText.setText(userInfo.hall)
            binding.bloodGroupTypeText.setText(userInfo.bloodgroup)
            binding.bloodGroupTypeText.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.text_red
                )
            )
            binding.occupationTypeText.setText(userInfo.occupation)
            binding.districtTypeText.setText(userInfo.district)

//            if (userInfo.subscription == "none") {
//                binding. status.text = "Inactive"
//                binding.status.setTextColor(
//                    ContextCompat.getColor(
//                        requireActivity(),
//                        R.color.text_red
//                    )
//                )
//            } else {
//                binding.status.text = "Active"
//                binding.status.setTextColor(
//                    ContextCompat.getColor(
//                        requireActivity(),
//                        R.color.green100
//                    )
//                )
//            }

            if (userInfo.imagePath == null) {
                binding.userProfilePic.hide()
                binding.profilePicAB.show()
                binding.profilePicAB.text = userInfo.name?.let { it1 ->
                    nameAbbreviationGenerator(
                        it1
                    )
                }
            } else {
                binding.userProfilePic.show()
                binding.profilePicAB.hide()

                val profileImg = Constants.IMG_PREFIX + userInfo.imagePath



                Glide.with(requireActivity())
                    .load(profileImg)
                    .placeholder(R.drawable.ic_mr)
                    .into(binding.userProfilePic)
            }
        }

    }

    override fun binObserver() {
        dashboardViewModel.occupationsVMLD.observe(viewLifecycleOwner) { occupations ->
            binding.progress.hide()

            when (occupations) {
                is NetworkResult.Error -> {


                }
                is NetworkResult.Loading -> {
                    binding.progress.show()


                }
                is NetworkResult.Success -> {

                    occupationList = occupations.data!!.occupations as ArrayList<Occupation>

                }

            }

        }
        dashboardViewModel.getDepartmentsVMLD.observe(viewLifecycleOwner) { departments ->
            binding.progress.hide()

            when (departments) {
                is NetworkResult.Error -> {


                }
                is NetworkResult.Loading -> {
                    binding.progress.show()


                }
                is NetworkResult.Success -> {

                    departmentList = departments.data!!.departments as ArrayList<Department>

                }

            }

        }
        dashboardViewModel.districtVMLD.observe(viewLifecycleOwner) { districts ->
            binding.progress.hide()

            when (districts) {
                is NetworkResult.Error -> {


                }
                is NetworkResult.Loading -> {
                    binding.progress.show()


                }
                is NetworkResult.Success -> {
                    districtList = districts.data!!.districts as ArrayList<District>


                }

            }

        }
        dashboardViewModel.hallsVMLD.observe(viewLifecycleOwner) { halls ->
            binding.progress.hide()

            when (halls) {
                is NetworkResult.Error -> {


                }
                is NetworkResult.Loading -> {
                    binding.progress.show()


                }
                is NetworkResult.Success -> {
                    hallList = halls.data!!.halls as ArrayList<Hall>


                }

            }

        }
        dashboardViewModel.getBloodGroupVMLD.observe(viewLifecycleOwner) { bloodGroups ->
            binding.progress.hide()

            when (bloodGroups) {
                is NetworkResult.Error -> {


                }
                is NetworkResult.Loading -> {
                    binding.progress.show()


                }
                is NetworkResult.Success -> {

                    bloodGroupList = bloodGroups.data!!.bloodgroups as ArrayList<Bloodgroup>

                }

            }

        }


    }

    //Departments
    private fun showBottomSheetDepartments() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_dialog)
        bottomSheetDialog.behavior.maxHeight = 1000 // set max height when expanded in PIXEL
        bottomSheetDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        // bottomSheetDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        bottomSheetDialog.findViewById<ImageView>(R.id.cancel_buttonSheet)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }


        //  bottomSheetDialog.behavior.peekHeight = 400 // set default height when collapsed in PIXEL
        // val copy = bottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)
        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.countryRecyclerView)



        buildDepartmentsRecyclerView(recyclerView!!)
        val searchView = bottomSheetDialog.findViewById<SearchView>(R.id.searchText)

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filterDepartments(newText)
                return false
            }
        })



        bottomSheetDialog.show()
    }

    private fun filterDepartments(text: String) {
        // creating a new array list to filter our data.
        val filteredlist =
            ArrayList<Department>()

        // running a for loop to compare elements.
        for (item in departmentList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name!!.lowercase(Locale.ROOT)
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
//            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            departmentAdapter.filterList(filteredlist)
        }
    }

    private fun buildDepartmentsRecyclerView(recyclerView: RecyclerView) {


        // initializing our adapter class.
        departmentAdapter = DepartmentAdapter(this, departmentList, requireContext())

        // adding layout manager to our recycler view.
        val manager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        // setting layout manager
        // to our recycler view.
        recyclerView.layoutManager = manager

        // setting adapter to
        // our recycler view.
        recyclerView.adapter = departmentAdapter


    }

    //Districts
    private fun showBottomSheetDistricts() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_dialog)
        bottomSheetDialog.behavior.maxHeight = 1000 // set max height when expanded in PIXEL
        bottomSheetDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        // bottomSheetDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        bottomSheetDialog.findViewById<ImageView>(R.id.cancel_buttonSheet)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }


        //  bottomSheetDialog.behavior.peekHeight = 400 // set default height when collapsed in PIXEL
        // val copy = bottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)
        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.countryRecyclerView)



        buildDistrictsRecyclerView(recyclerView!!)
        val searchView = bottomSheetDialog.findViewById<SearchView>(R.id.searchText)

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filterDistricts(newText)
                return false
            }
        })



        bottomSheetDialog.show()
    }

    private fun filterDistricts(text: String) {
        // creating a new array list to filter our data.
        val filteredlist =
            ArrayList<District>()

        // running a for loop to compare elements.
        for (item in districtList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name.lowercase(Locale.ROOT)
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
//            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            districtAdapter.filterList(filteredlist)
        }
    }

    private fun buildDistrictsRecyclerView(recyclerView: RecyclerView) {


        // initializing our adapter class.
        districtAdapter = DistrictAdapter(this, districtList, requireContext())

        // adding layout manager to our recycler view.
        val manager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        // setting layout manager
        // to our recycler view.
        recyclerView.layoutManager = manager

        // setting adapter to
        // our recycler view.
        recyclerView.adapter = districtAdapter


    }

    //BloodGroup
    private fun showBottomSheetBloodGroup() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_dialog)
        bottomSheetDialog.behavior.maxHeight = 1000 // set max height when expanded in PIXEL
        bottomSheetDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        // bottomSheetDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        bottomSheetDialog.findViewById<ImageView>(R.id.cancel_buttonSheet)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }


        //  bottomSheetDialog.behavior.peekHeight = 400 // set default height when collapsed in PIXEL
        // val copy = bottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)
        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.countryRecyclerView)



        buildBloodGroupRecyclerView(recyclerView!!)
        val searchView = bottomSheetDialog.findViewById<SearchView>(R.id.searchText)

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filterOccupation(newText)
                return false
            }
        })



        bottomSheetDialog.show()
    }

    private fun filterBloodGroup(text: String) {
        // creating a new array list to filter our data.
        val filteredlist =
            ArrayList<Occupation>()

        // running a for loop to compare elements.
        for (item in occupationList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name.lowercase(Locale.ROOT)
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
//            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            occupationAdapter.filterList(filteredlist)
        }
    }

    private fun buildBloodGroupRecyclerView(recyclerView: RecyclerView) {


        // initializing our adapter class.
        bloodGroupAdapter = BloodGroupAdapter(this, bloodGroupList, requireContext())

        // adding layout manager to our recycler view.
        val manager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        // setting layout manager
        // to our recycler view.
        recyclerView.layoutManager = manager

        // setting adapter to
        // our recycler view.
        recyclerView.adapter = bloodGroupAdapter


    }

    //Occupation
    private fun showBottomSheetOccupation() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_dialog)
        bottomSheetDialog.behavior.maxHeight = 1000 // set max height when expanded in PIXEL
        bottomSheetDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        // bottomSheetDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        bottomSheetDialog.findViewById<ImageView>(R.id.cancel_buttonSheet)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }


        //  bottomSheetDialog.behavior.peekHeight = 400 // set default height when collapsed in PIXEL
        // val copy = bottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)
        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.countryRecyclerView)



        buildOccupationRecyclerView(recyclerView!!)
        val searchView = bottomSheetDialog.findViewById<SearchView>(R.id.searchText)

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filterBloodGroup(newText)
                return false
            }
        })



        bottomSheetDialog.show()
    }

    private fun filterOccupation(text: String) {
        // creating a new array list to filter our data.
        val filteredlist =
            ArrayList<Bloodgroup>()

        // running a for loop to compare elements.
        for (item in bloodGroupList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name!!.lowercase(Locale.ROOT)
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
//            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            bloodGroupAdapter.filterList(filteredlist)
        }
    }

    private fun buildOccupationRecyclerView(recyclerView: RecyclerView) {


        // initializing our adapter class.
        occupationAdapter = OccupationAdapter(this, occupationList, requireContext())

        // adding layout manager to our recycler view.
        val manager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        // setting layout manager
        // to our recycler view.
        recyclerView.layoutManager = manager

        // setting adapter to
        // our recycler view.
        recyclerView.adapter = occupationAdapter


    }

    //Hall
    private fun showBottomSheetHall() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_dialog)
        bottomSheetDialog.behavior.maxHeight = 1000 // set max height when expanded in PIXEL
        bottomSheetDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        // bottomSheetDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        bottomSheetDialog.findViewById<ImageView>(R.id.cancel_buttonSheet)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }


        //  bottomSheetDialog.behavior.peekHeight = 400 // set default height when collapsed in PIXEL
        // val copy = bottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)
        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.countryRecyclerView)



        buildHallRecyclerView(recyclerView!!)
        val searchView = bottomSheetDialog.findViewById<SearchView>(R.id.searchText)

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filterHall(newText)
                return false
            }
        })



        bottomSheetDialog.show()
    }

    private fun filterHall(text: String) {
        // creating a new array list to filter our data.
        val filteredlist =
            ArrayList<Hall>()

        // running a for loop to compare elements.
        for (item in hallList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name!!.lowercase(Locale.ROOT)
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
//            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            hallAdapter.filterList(filteredlist)
        }
    }

    private fun buildHallRecyclerView(recyclerView: RecyclerView) {


        // initializing our adapter class.
        hallAdapter = HallAdapter(this, hallList, requireContext())

        // adding layout manager to our recycler view.
        val manager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        // setting layout manager
        // to our recycler view.
        recyclerView.layoutManager = manager

        // setting adapter to
        // our recycler view.
        recyclerView.adapter = hallAdapter


    }

    override fun selectedDepartment(departments: Department) {
        binding.departmentTypeText.text = departments.name
        bottomSheetDialog.dismiss()
    }

    override fun selectedDistrict(departments: District) {
        binding.districtTypeText.text = departments.name
        bottomSheetDialog.dismiss()
    }

    override fun selectedBloodGroup(bloodGroup: Bloodgroup) {
        binding.bloodGroupTypeText.text = bloodGroup.name
        bottomSheetDialog.dismiss()
    }

    override fun selectedOccupation(occupation: Occupation) {
        binding.occupationTypeText.text = occupation.name
        bottomSheetDialog.dismiss()
    }

    override fun selectedHall(hall: Hall) {
        binding.hallTypeText.text = hall.name
        bottomSheetDialog.dismiss()
    }

}