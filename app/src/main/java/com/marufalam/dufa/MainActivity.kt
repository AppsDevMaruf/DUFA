package com.marufalam.dufa


import android.annotation.SuppressLint
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import com.marufalam.dufa.data.local.TokenManager
import com.marufalam.dufa.data.models.getProfileInfo.Profile
import com.marufalam.dufa.databinding.ActivityMainBinding
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var tokenManager: TokenManager
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var userProfilePic: ShapeableImageView

    private lateinit var progressBar: ProgressBar
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private var bundle = Bundle()
    lateinit var binding: ActivityMainBinding
    private lateinit var userProfilePicHeader: ShapeableImageView
    private lateinit var uploadProfilePic: ShapeableImageView
    private lateinit var userProfilePicABHeader: TextView
    companion object {
        private val PERMISSIONS = arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        )
    }

    private lateinit var permissionsRequest: ActivityResultLauncher<Array<String>>
    private val customCropImage = registerForActivityResult(CropImageContract()) {
        if (it !is CropImage.CancelledResult) {
            handleCropImageResult(it.uriContent.toString())
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionsRequest = getPermissionsRequest()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val nav = binding.navigationView.getHeaderView(0)

        userProfilePicHeader = nav.findViewById(R.id.userProfilePicHeader)

        userProfilePicABHeader = nav.findViewById(R.id.profilePicABHeader)

        uploadProfilePic = nav.findViewById(R.id.uploadProfilePic)

        uploadProfilePic.setOnClickListener {
            requestPermissions(permissionsRequest, PERMISSIONS)
            hideSoftKeyboard()
        }


        dashboardViewModel.profileInfoVM()
        binObserver()

        ///
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.navigationView)
        navView.itemIconTintList = null;


        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        userProfilePic = toolbar.findViewById(R.id.userProfilePic)

        progressBar = findViewById(R.id.progress)
        userProfilePic.setOnClickListener {
            navController.navigate(R.id.profileFragment,bundle)

            Toast.makeText(applicationContext, "Your Image not Uploading Yet... ", Toast.LENGTH_SHORT).show()
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.DashboardFragment, R.id.logInFragment, R.id.signUpFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }
    private fun getPermissionsRequest() =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (isAllPermissionsGranted(PERMISSIONS)) {
                showImagePickerDialog()
            } else {

            }
        }
    @SuppressLint("SetTextI18n")
    private fun handleCropImageResult(uri: String) {
        binding.navigationView.getHeaderView(0).findViewById<ShapeableImageView>(R.id.userProfilePicHeader).setImageURI(Uri.parse(uri))
      /*  setKycData(KycData.BUSINESS_LOGO, uri)

        binding.businessLogo.show()

        binding.fileName.text = "image-${System.currentTimeMillis()}.$extension"
        binding.fileName.show()


        hasLogo = true
        enableBtn(
            (hasLogo && isCountrySelected && hasBusinessRegNum && hasBusinessName),
            binding.nextBtn
        )*/

    }

    private fun showImagePickerDialog() {

        val dialog = Dialog(this)
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


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun binObserver() {
        dashboardViewModel.profileInfoVMLD.observe(this) {
            progressBar.isVisible = false
            when (it) {

                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {
                    progressBar.isVisible = true

                }
                is NetworkResult.Success -> {

                    setData(it.data?.profile)

                }


            }

        }
    }

    private fun setData(profile: List<Profile?>?) {
        if (profile != null) {
            bundle.putParcelable("userinfo", profile[0])  // Key, value
            binding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.userName).text =
                profile[0]?.name
            binding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.userEmail).text =
                profile[0]?.email
        }
        if (profile?.get(0)?.imagePath == null) {

            userProfilePicHeader.hide()
            userProfilePicABHeader.show()
            userProfilePicABHeader.text = nameAbbreviationGenerator(profile!![0]?.name.toString())
        } else {
            userProfilePicHeader.show()
            userProfilePicABHeader.hide()

            val profilePic = Constants.IMG_PREFIX + profile[0]?.imagePath
            val url = GlideUrl(
                profilePic,
                GlideUtils.glideHeaders(tokenManager.getToken(Constants.TOKEN))
            )
            Glide.with(applicationContext)
                .load(url)
                .placeholder(R.drawable.loadpreview)
                .into(userProfilePic)
            Glide.with(applicationContext)
                .load(url)
                .placeholder(R.drawable.loadpreview)
                .into(userProfilePicHeader)
        }


    }


}




