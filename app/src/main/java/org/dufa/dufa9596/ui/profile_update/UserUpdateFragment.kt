package org.dufa.dufa9596.ui.profile_update

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.net.Uri
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.dufa.dufa9596.BaseFragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.data.models.getProfileInfo.ResponseProfileInfo
import org.dufa.dufa9596.data.models.get_departments.Department
import org.dufa.dufa9596.data.models.get_districts.District
import org.dufa.dufa9596.data.models.get_halls.Hall
import org.dufa.dufa9596.data.models.get_occupations.Occupation
import org.dufa.dufa9596.data.models.search.blood.Bloodgroup
import org.dufa.dufa9596.databinding.FragmentUserUpdateBinding
import org.dufa.dufa9596.interfaces.BloodGroupSelectListener
import org.dufa.dufa9596.interfaces.DepartmentSelectListener
import org.dufa.dufa9596.interfaces.DistrictSelectListener
import org.dufa.dufa9596.interfaces.HallSelectListener
import org.dufa.dufa9596.interfaces.OccupationSelectListener
import org.dufa.dufa9596.ui.profile_update.adapter.BloodGroupAdapter
import org.dufa.dufa9596.ui.profile_update.adapter.DepartmentAdapter
import org.dufa.dufa9596.ui.profile_update.adapter.DistrictAdapter
import org.dufa.dufa9596.ui.profile_update.adapter.HallAdapter
import org.dufa.dufa9596.ui.profile_update.adapter.OccupationAdapter
import org.dufa.dufa9596.utils.Constants
import org.dufa.dufa9596.utils.NetworkResult
import org.dufa.dufa9596.utils.datePickerFun
import org.dufa.dufa9596.utils.hide
import org.dufa.dufa9596.utils.hideSoftKeyboard
import org.dufa.dufa9596.utils.isAllPermissionsGranted
import org.dufa.dufa9596.utils.loadImagesWithGlide
import org.dufa.dufa9596.utils.nameAbbreviationGenerator
import org.dufa.dufa9596.utils.requestPermissions
import org.dufa.dufa9596.utils.show
import org.dufa.dufa9596.viewmodel.DashboardViewModel
import java.io.File
import java.io.FileOutputStream
import java.util.Locale

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

    lateinit var dialog: ProgressDialog

    var userid = 0

    companion object {
        private val PERMISSIONS = arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        )
    }

    private lateinit var permissionsRequest: ActivityResultLauncher<Array<String>>
    private val customCropImage = registerForActivityResult(CropImageContract()) {
        if (it !is CropImage.CancelledResult) {
            handleCropImageResult(it.uriContent!!)
        }
    }

    override fun getFragmentView(): Int {
        dialog = ProgressDialog(requireContext())
        dialog.setTitle("Profile Updating...")


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

        binding.userProfilePic.setOnClickListener {
            requestPermissions(permissionsRequest, PERMISSIONS)
            startCameraWithoutUri(includeCamera = true, includeGallery = true)

        }
        binding.profilePicAB.setOnClickListener {
            requestPermissions(permissionsRequest, PERMISSIONS)
            startCameraWithoutUri(includeCamera = true, includeGallery = true)

        }

        binding.uploadProfilePicBtn.setOnClickListener {
            requestPermissions(permissionsRequest, PERMISSIONS)
            startCameraWithoutUri(includeCamera = true, includeGallery = true)
        }

        binding.updateBtn.setOnClickListener {
            val name = binding.name.text.toString()
            val phone = binding.phone.text.toString()
            val address = binding.address.text.toString()
            val department = binding.departmentTypeText.text.toString()
            val district = binding.districtTypeText.text.toString()
            val occupation = binding.occupationTypeText.text.toString()
            val bloodGroup = binding.bloodGroupTypeText.text.toString()
            val gender = binding.genderTypeText.text.toString()
            val hall = binding.hallTypeText.text.toString()
            val nid = binding.nid.text.toString()
            val birthdate = binding.birthdate.text.toString()

            val request = RequestProfileUpdate(
                name = name,
                address = address,
                phone = phone,
                " ",
                department = department,
                district = district,
                occupation = occupation,
                bloodgroup = bloodGroup,
                gender = gender,
                hall = hall,
                nid = nid,
                birthdate = birthdate
            )
            dialog.setCancelable(false)
            dialog.show()
            if (phone.trim() != "" && address.trim() != "") {
                try {
                    CoroutineScope(Dispatchers.IO).launch {
                        dashboardViewModel.updateProfileVM(userid, requestProfileUpdate = request)
                    }
                } catch (e: Exception) {

                    Log.e("TAG", "Exception: ${e.message} ")
                }

                dialog.setCancelable(false)
                dialog.show()
            } else {
                if (phone.trim() == "") {
                    binding.phone.error = "Phone Number Empty"
                } else {
                    binding.address.error = "Address Empty"
                }
                dialog.dismiss()
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
                    R.color.text_primary
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
                    R.color.text_primary
                )
            )
            binding.genderIcon.setImageResource(R.drawable.ic_mrs)

            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    private fun getPermissionsRequest() =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (isAllPermissionsGranted(PERMISSIONS)) {
                showImagePickerDialog()
            } else {

            }
        }


    private fun handleCropImageResult(uri: Uri) {
        upload(uri)
        binding.userProfilePic.show()
        binding.profilePicAB.hide()
        binding.userProfilePic.setImageURI(uri)


    }

    private fun upload(fileUri: Uri) {
        dialog.setCancelable(false)
        dialog.show()

        val filesDir = requireActivity().filesDir
        val file = File(filesDir, "profile${System.currentTimeMillis()}.png")


        val inputStream = requireActivity().contentResolver.openInputStream(fileUri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)


        CoroutineScope(Dispatchers.IO).launch {
            val nFile = Compressor.compress(requireContext(), file)

            val requestBody = nFile.asRequestBody("image/*".toMediaTypeOrNull())

            val part = MultipartBody.Part.createFormData("profile_pic", file.name, requestBody)

            dashboardViewModel.uploadProfilePicVM(userId = userid, part = part)
        }


        inputStream.close()
        outputStream.close()

    }

    private fun showImagePickerDialog() {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_custom_layout)

        dialog.findViewById<TextView>(R.id.cancel_button).setOnClickListener {
            dialog.dismiss()
        }

        val galleryBtn = dialog.findViewById(R.id.galleryBtn) as TextView
        val cameraBtn = dialog.findViewById(R.id.cameraBtn) as TextView


        galleryBtn.setOnClickListener {
            startCameraWithoutUri(includeCamera = false, includeGallery = true)
            dialog.dismiss()
        }

        cameraBtn.setOnClickListener {
            startCameraWithoutUri(includeCamera = true, includeGallery = false)
            dialog.dismiss()
        }

        dialog.show()

    }

    private fun startCameraWithoutUri(includeCamera: Boolean, includeGallery: Boolean) {
        customCropImage.launch(
            CropImageContractOptions(
                uri = null,
                cropImageOptions = CropImageOptions(
                    imageSourceIncludeCamera = includeCamera,
                    imageSourceIncludeGallery = includeGallery,
                ),
            ),
        )
    }

    override fun configUi() {
        permissionsRequest = getPermissionsRequest()
        dashboardViewModel.profileInfoVM()
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
            binding.genderTypeText.text = userInfo.gender
            binding.birthdate.text = userInfo.birthdate
            binding.departmentTypeText.text = userInfo.department
            binding.hallTypeText.text = userInfo.hall
            binding.occupationTypeText.text = userInfo.occupation
            binding.bloodGroupTypeText.text = userInfo.bloodgroup
            binding.bloodGroupTypeText.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.text_red
                )
            )

            binding.districtTypeText.text = userInfo.district

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

                binding.userProfilePic.loadImagesWithGlide(profileImg)

            }
        }

    }

    override fun binObserver() {

        dashboardViewModel.responseUpdateProfileVMLD.observe(viewLifecycleOwner) {

            when (it) {
                is NetworkResult.Error -> {
                    dialog.dismiss()


                }

                is NetworkResult.Loading -> {
                    dialog.dismiss()


                }

                is NetworkResult.Success -> {
                    dialog.dismiss()

                    Log.i("TAG", "message: ${it.message}")
                    Log.i("TAG", "data: ${it.data?.message}")

                    //   it.data?.let { it1 -> toast(it1.message) }


                    findNavController().popBackStack()


                }
            }


        }
        dashboardViewModel.uploadProfilePicVMLD.observe(viewLifecycleOwner) {

            when (it) {
                is NetworkResult.Error -> {
                    dialog.dismiss()
                    Log.i("TAG", "Error: ${it.data.toString()}")

                }

                is NetworkResult.Loading -> {
                    dialog.dismiss()

                    Log.i("TAG", "Loading: ${it.data}")
                }

                is NetworkResult.Success -> {
                    dialog.dismiss()

                    Log.i("TAG", "message: ${it.message}")
                    Log.i("TAG", "data: ${it.data?.message}")

                    //   it.data?.let { it1 -> toast(it1.message) }

                }
            }


        }

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

        dashboardViewModel.profileInfoVMLD.observe(viewLifecycleOwner) {
            //progressBar.isVisible = false
            when (it) {

                is NetworkResult.Error -> {
                    Log.i("Error", "NetworkResult.Error: ${it.data!!}")
                    //Log.i("TAG1", "binObserver: ${it.data!!.message.toString()}")
                }

                is NetworkResult.Loading -> {
                    // progressBar.isVisible = true

                }

                is NetworkResult.Success -> {
                    userid = it.data?.id!!


                }

            }

        }


    }

    //Departments
    @SuppressLint("SetTextI18n")
    private fun showBottomSheetDepartments() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_dialog)
        bottomSheetDialog.behavior.maxHeight = 1000 // set max height when expanded in PIXEL
        bottomSheetDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        // bottomSheetDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        bottomSheetDialog.findViewById<ImageView>(R.id.cancel_buttonSheet)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val filterType = bottomSheetDialog.findViewById<TextView>(R.id.filterTypeId)
        filterType!!.text = "Select a department"

        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.countryRecyclerView)
        buildDepartmentsRecyclerView(recyclerView!!)

        bottomSheetDialog.show()
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


        val filterType = bottomSheetDialog.findViewById<TextView>(R.id.filterTypeId)
        filterType!!.text = "Select a district"

        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.countryRecyclerView)

        buildDistrictsRecyclerView(recyclerView!!)
        bottomSheetDialog.show()
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
    @SuppressLint("SetTextI18n")
    private fun showBottomSheetBloodGroup() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_dialog)
        bottomSheetDialog.behavior.maxHeight = 1000 // set max height when expanded in PIXEL
        bottomSheetDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        // bottomSheetDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        bottomSheetDialog.findViewById<ImageView>(R.id.cancel_buttonSheet)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val filterType = bottomSheetDialog.findViewById<TextView>(R.id.filterTypeId)
        filterType!!.text = "Select a Blood group"

        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.countryRecyclerView)



        buildBloodGroupRecyclerView(recyclerView!!)

        bottomSheetDialog.show()
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
    @SuppressLint("SetTextI18n")
    private fun showBottomSheetOccupation() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_dialog)
        bottomSheetDialog.behavior.maxHeight = 1000 // set max height when expanded in PIXEL
        bottomSheetDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        // bottomSheetDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        bottomSheetDialog.findViewById<ImageView>(R.id.cancel_buttonSheet)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }


        val filterType = bottomSheetDialog.findViewById<TextView>(R.id.filterTypeId)
        filterType!!.text = "Select an occupation"
        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.countryRecyclerView)



        buildOccupationRecyclerView(recyclerView!!)
        bottomSheetDialog.show()
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
    @SuppressLint("SetTextI18n")
    private fun showBottomSheetHall() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_dialog)
        bottomSheetDialog.behavior.maxHeight = 1000 // set max height when expanded in PIXEL
        bottomSheetDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        // bottomSheetDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        bottomSheetDialog.findViewById<ImageView>(R.id.cancel_buttonSheet)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        val filterType = bottomSheetDialog.findViewById<TextView>(R.id.filterTypeId)
        filterType!!.text = "Select a hall"
        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.countryRecyclerView)



        buildHallRecyclerView(recyclerView!!)
        bottomSheetDialog.show()
    }


    private fun buildHallRecyclerView(recyclerView: RecyclerView) {

        // initializing our adapter class.
        hallAdapter = HallAdapter(this, hallList, requireContext())

        // adding layout manager to our recycler view.
        val manager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = manager

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