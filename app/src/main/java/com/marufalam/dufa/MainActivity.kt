package com.marufalam.dufa


import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import com.marufalam.dufa.data.local.TokenManager
import com.marufalam.dufa.data.models.getProfileInfo.ResponseProfileInfo
import com.marufalam.dufa.databinding.ActivityMainBinding
import com.marufalam.dufa.ui.LogInActivity
import com.marufalam.dufa.ui.profile_update.UserUpdateFragment
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import id.zelory.compressor.Compressor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    @Inject
    lateinit var tokenManager: TokenManager
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var userProfilePic: ShapeableImageView
    private var userId = 0
    private lateinit var progressBar: ProgressBar
    private val dashboardViewModel: DashboardViewModel by viewModels()

    private var bundle = Bundle()
    lateinit var binding: ActivityMainBinding
    private lateinit var userProfilePicHeader: ShapeableImageView
    private lateinit var uploadProfilePicBtn: CircleImageView

    private lateinit var userProfilePicABHeader: TextView
    private lateinit var profilePicAB: TextView

    private lateinit var toolbar: Toolbar

    lateinit var titleAb: TextView
    lateinit var topImage: ImageView

    lateinit var nav: View

    lateinit var dialog: ProgressDialog

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        //  supportActionBar?.hide()
        setContentView(binding.root)

        permissionsRequest = getPermissionsRequest()

        dialog = ProgressDialog(this)
        dialog.setTitle("Profile Updating...")

        nav = binding.navigationView.getHeaderView(0)

        userProfilePicHeader = nav.findViewById(R.id.userProfilePicHeader)
        uploadProfilePicBtn = nav.findViewById(R.id.uploadProfilePicBtn)

        userProfilePicABHeader = nav.findViewById(R.id.profilePicABHeader)

        dashboardViewModel.profileInfoVM()
        binObserver()

        ///
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.navigationView)
        navView.itemIconTintList = null;


        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        userProfilePic = toolbar.findViewById(R.id.userProfilePic)
        profilePicAB = toolbar.findViewById(R.id.profilePicAB)

        progressBar = findViewById(R.id.progress)
        userProfilePic.setOnClickListener {
            navController.navigate(R.id.profileFragment, bundle)

        }
        profilePicAB.setOnClickListener {
            navController.navigate(R.id.profileFragment, bundle)

        }

        uploadProfilePicBtn.setOnClickListener {

            requestPermissions(permissionsRequest, PERMISSIONS)
            startCameraWithoutUri(includeCamera = true, includeGallery = false)

        }




        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.DashboardFragment,
//                R.id.memberListFragment,
//                R.id.duesPaymentFragment,
//                R.id.logoutFragment,
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
       // navView.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        when (item.itemId) {

            R.id.logoutFragment -> {

                startActivity(Intent(this@MainActivity, LogInActivity::class.java))

                finish()

            }


        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun binObserver() {


        dashboardViewModel.uploadProfilePicVMLD.observe(this) {

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
        dashboardViewModel.profileInfoVMLD.observe(this) {
            progressBar.isVisible = false
            when (it) {

                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {
                    progressBar.isVisible = true

                }
                is NetworkResult.Success -> {
                    Log.i("TAG", "binObserver: $it ")

                    it.data?.let { it1 -> setData(it1) }

                }


            }

        }

    }

    private fun setData(profile: ResponseProfileInfo) {
        Log.i("TAG", "setData: $profile ")
        titleAb = toolbar.findViewById(R.id.profilePicAB)
        topImage = toolbar.findViewById(R.id.userProfilePic)

        nav.findViewById<TextView>(R.id.userName).text = profile.name
        nav.findViewById<TextView>(R.id.userEmail).text = profile.email


        userId = profile.id
        bundle.putParcelable("userinfo", profile)  // Key, value
        binding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.userName).text =
            profile.name
        binding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.userEmail).text =
            profile.email
        if (profile.imagePath == null) {

            userProfilePicHeader.hide()
            userProfilePicABHeader.show()
            userProfilePic.hide()
            profilePicAB.show()



            titleAb.show()
            topImage.hide()
            titleAb.text = nameAbbreviationGenerator(profile.name.toString())
            userProfilePicABHeader.text = nameAbbreviationGenerator(profile.name.toString())


        } else {
            userProfilePicHeader.show()
            userProfilePicABHeader.hide()
            userProfilePic.show()
            profilePicAB.hide()

            val profilePic = Constants.IMG_PREFIX + profile.imagePath
            topImage.show()
            titleAb.hide()

            userProfilePicHeader.loadImagesWithGlide(profilePic)

            topImage.loadImagesWithGlide(profilePic)

//

        }


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

    private fun getPermissionsRequest() =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (isAllPermissionsGranted(PERMISSIONS)) {
                showImagePickerDialog()
            } else {

            }
        }


    private fun handleCropImageResult(uri: Uri) {
        upload(uri)

        userProfilePicHeader.setImageURI(uri)


    }

    private fun upload(fileUri: Uri) {
        dialog.setCancelable(false)
        dialog.show()

        val filesDir = filesDir
        val file = File(filesDir, "profile${System.currentTimeMillis()}.png")

        Log.i("uploadFileDir", "upload:$file")

        val inputStream = contentResolver.openInputStream(fileUri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)


        CoroutineScope(Dispatchers.IO).launch {
            val nFile = Compressor.compress(this@MainActivity, file)

            val requestBody = nFile.asRequestBody("image/*".toMediaTypeOrNull())

            val part = MultipartBody.Part.createFormData("profile_pic", file.name, requestBody)

            dashboardViewModel.uploadProfilePicVM(userId = userId, part = part)
        }


        inputStream.close()
        outputStream.close()

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
}






