package org.dufa.dufa9596

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView

import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.dufa.dufa9596.data.local.TokenManager
import org.dufa.dufa9596.data.models.getProfileInfo.ResponseProfileInfo
import org.dufa.dufa9596.data.models.locations.RequestSetCLocation
import org.dufa.dufa9596.databinding.ActivityMainBinding
import org.dufa.dufa9596.utils.Constants
import org.dufa.dufa9596.utils.NetworkResult
import org.dufa.dufa9596.utils.gone
import org.dufa.dufa9596.utils.hide
import org.dufa.dufa9596.utils.isAllPermissionsGranted
import org.dufa.dufa9596.utils.loadImagesWithGlide
import org.dufa.dufa9596.utils.nameAbbreviationGenerator
import org.dufa.dufa9596.utils.requestPermissions
import org.dufa.dufa9596.utils.show
import org.dufa.dufa9596.utils.showDialog
import org.dufa.dufa9596.viewmodel.DashboardViewModel
import java.io.File
import java.io.FileOutputStream
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var tokenManager: TokenManager
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var userProfilePic: ShapeableImageView
    private var userId = 0
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2
    private var dues: Int? = null
    private var voucher: Int? = null
    private var bundle = Bundle()

    private val dashboardViewModel by viewModels <DashboardViewModel>()
    lateinit var binding: ActivityMainBinding
    private lateinit var userProfilePicHeader: ShapeableImageView
    private lateinit var uploadProfilePicBtn: ImageView
    private lateinit var userProfilePicABHeader: TextView
    private lateinit var profilePicAB: TextView
    private lateinit var toolbar: Toolbar
    lateinit var titleAb: TextView
    lateinit var topImage: ImageView
    lateinit var nav: View
    lateinit var dialog: ProgressDialog

    companion object {
        private val PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
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
        /* val pInfo: PackageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
         val version: String = pInfo.versionName //Version Name*/

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        permissionsRequest = getPermissionsRequest()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
        dialog = ProgressDialog(this)
        dialog.setTitle("Profile Updating...")

        nav = binding.navigationView.getHeaderView(0)

        userProfilePicHeader = nav.findViewById(R.id.userProfilePicHeader)
        uploadProfilePicBtn = nav.findViewById(R.id.uploadProfilePicBtn)

        userProfilePicABHeader = nav.findViewById(R.id.profilePicABHeader)

        dashboardViewModel.profileInfoVM()
        dashboardViewModel.dashboardInfoVM()
        binObserver()


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


        userProfilePic.setOnClickListener {
            navController.navigate(R.id.profileFragment)

        }
        profilePicAB.setOnClickListener {
            navController.navigate(R.id.profileFragment)

        }

        uploadProfilePicBtn.setOnClickListener {

            requestPermissions(permissionsRequest, PERMISSIONS)
            startCameraWithoutUri(includeCamera = true, includeGallery = true)

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
        //navView.menu.findItem(R.id.nav_log_version).title = "version $version"
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.duesPaymentFragment -> {
                    if (dues!! > 0) {
                        bundle.putInt("dues", dues!!)
                        Log.i("TAG", "onCreate2222: $dues")
                        navController.navigateUp() // to clear previous navigation history
                        navController.navigate(R.id.duesPaymentFragment, bundle)
                    } else {
                        navController.navigateUp() // to clear previous navigation history
                        navController.navigate(R.id.transactionHistoryFragment)
                    }

                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    false
                }

                R.id.memberListFragment -> {
                    navController.navigateUp() // to clear previous navigation history
                    navController.navigate(R.id.memberListFragment)
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    false
                }

                R.id.mapsFragment -> {
                    navController.navigateUp() // to clear previous navigation history
                    navController.navigate(R.id.mapsFragment)
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    false
                }

                R.id.voucherListFragment -> {
                    if (voucher == 0) {
                        navController.navigateUp() // to clear previous navigation history
                        navController.navigate(R.id.voucherFragment)
                    } else {
                        navController.navigateUp() // to clear previous navigation history
                        navController.navigate(R.id.voucherListFragment)
                    }
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    false
                }

                R.id.fundCollectionFragment -> {
                    navController.navigateUp() // to clear previous navigation history
                    navController.navigate(R.id.fundCollectionFragment)
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    false
                }

                R.id.QRFragment -> {
                    navController.navigateUp() // to clear previous navigation history
                    navController.navigate(R.id.QRFragment)
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    false
                }

                R.id.logoutFragment -> {
                    navController.navigateUp() // to clear previous navigation history
                    navController.navigate(R.id.logoutFragment)
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    false
                }

                else -> {
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    false
                }
            }
        }

    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())

                        val list: List<Address> = geocoder.getFromLocation(
                            location.latitude,
                            location.longitude,
                            1
                        ) as List<Address>
                        Log.i(
                            "TAG",
                            "LoginPageGetLocation: ${list[0].latitude}\n${list[0].longitude}"
                        )

                        val requestSetCLocation = RequestSetCLocation(
                            userId,
                            cityName = list[0].adminArea,
                            latitude = list[0].latitude,
                            longitude = list[0].longitude
                        )
                        Log.e("TAG", "requestSetCLocation: $requestSetCLocation")
                        dashboardViewModel.setCurrentLocationVM(requestSetCLocation)
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }


    override fun onResume() {
        super.onResume()
        dashboardViewModel.profileInfoVM()
        dashboardViewModel.dashboardInfoVM()
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
                    showDialog(
                        context = this,
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
                    dialog.dismiss()
                }

                is NetworkResult.Success -> {
                    dialog.dismiss()

                }
            }


        }
        dashboardViewModel.profileInfoVMLD.observe(this) {
            binding.progress.isVisible = false
            when (it) {

                is NetworkResult.Error -> {
                    showDialog(
                        context = this,
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
                    binding.progress.isVisible = true

                }

                is NetworkResult.Success -> {
                    Log.i("TAG", "binObserver: $it ")

                    it.data?.let { it1 -> setData(it1) }

                }


            }

        }
        dashboardViewModel.dashboardInfoVMLD.observe(this) {
            binding.progress.isVisible = false
            when (it) {
                is NetworkResult.Error -> {
                    showDialog(
                        context = this,
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
                    binding.progress.isVisible = true
                }

                is NetworkResult.Success -> {
                    dues = it.data?.totalDues

                    Log.i("TAG", "load111111: $dues")
                    voucher = it.data?.totalVoucher


                }


            }

        }
        dashboardViewModel.setCurrentLocationVMLD.observe(this) {
            binding.progress.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    Log.i("Success", "setCurrentLocationVMLD: ${it.data?.message}")
                }

                is NetworkResult.Error -> {
                    showDialog(
                        context = this,
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
                    binding.progress.isVisible = true
                }
            }
        }

    }

    private fun setData(profile: ResponseProfileInfo) {
        titleAb = toolbar.findViewById(R.id.profilePicAB)
        topImage = toolbar.findViewById(R.id.userProfilePic)

        nav.findViewById<TextView>(R.id.userName).text = profile.name
        nav.findViewById<TextView>(R.id.userEmail).text = profile.email


        userId = profile.id

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






