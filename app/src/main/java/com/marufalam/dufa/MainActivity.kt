package com.marufalam.dufa


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import com.marufalam.dufa.data.local.TokenManager
import com.marufalam.dufa.data.models.getProfileInfo.ResponseProfileInfo
import com.marufalam.dufa.databinding.ActivityMainBinding
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.viewmodel.AuthViewModel
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var tokenManager: TokenManager
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var userProfilePic: ShapeableImageView
    private var userId = 0
    private lateinit var progressBar: ProgressBar
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    private var bundle = Bundle()
    lateinit var binding: ActivityMainBinding
    private lateinit var userProfilePicHeader: ShapeableImageView

    private lateinit var userProfilePicABHeader: TextView
    private lateinit var profilePicAB: TextView

    private lateinit var toolbar: Toolbar

    lateinit var titleAb: TextView
    lateinit var topImage: ImageView

    lateinit var nav: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        //  supportActionBar?.hide()
        setContentView(binding.root)

        nav = binding.navigationView.getHeaderView(0)

        userProfilePicHeader = nav.findViewById(R.id.userProfilePicHeader)

        userProfilePicABHeader = nav.findViewById(R.id.profilePicABHeader)





        authViewModel.loginResponseToken.observe(this) {
            if (it.status) {
                dashboardViewModel.profileInfoVM()
            }
        }


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


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.DashboardFragment, R.id.signUpFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

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
}






