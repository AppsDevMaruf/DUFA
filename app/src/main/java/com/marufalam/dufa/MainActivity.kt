package com.marufalam.dufa


import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
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
    private lateinit var userProfilePicABHeader: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var nav = binding.navigationView.getHeaderView(0)

        userProfilePicHeader = nav.findViewById(R.id.userProfilePicHeader)

        userProfilePicABHeader = nav.findViewById(R.id.profilePicABHeader)


        dashboardViewModel.getMyProfileInfoVM()
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
            Toast.makeText(
                applicationContext,
                "Your Image not Uploading Yet... ",
                Toast.LENGTH_SHORT
            ).show()
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.DashboardFragment, R.id.logInFragment, R.id.signUpFragment
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
        dashboardViewModel.getMyProfileInfoVMLD.observe(this) {
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




