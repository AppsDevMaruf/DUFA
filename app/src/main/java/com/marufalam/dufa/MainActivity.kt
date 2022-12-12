package com.marufalam.dufa


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import com.marufalam.dufa.data.models.dashboard.ResponseMemberList
import com.marufalam.dufa.db.room.*
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dashboardViewModel.getMemberList()
        //binObserver()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val userProfilePic = toolbar.findViewById<ShapeableImageView>(R.id.userProfilePic)
        userProfilePic.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "Your Image not Uploading Yet... ",
                Toast.LENGTH_SHORT
            ).show()
        }
        ///
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.navigationView)
        navView.itemIconTintList = null;


        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
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


    /*fun binObserver() {
        dashboardViewModel.getMemberListResponse.observe(this) {

            when (it) {


                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {
                    setDataToRoom(it.data)


                }


            }

        }

    }*/

   /* private fun setDataToRoom(responseMemberList: ResponseMemberList?) {

        val departmentSet = mutableSetOf<String>()
        val districtSet = mutableSetOf<String>()
        val bloodGroupSet = mutableSetOf<String>()
        val professionSet = mutableSetOf<String>()

        responseMemberList?.users.let { it ->

            it?.forEach {
                if (it!=null){

                    it.department?.let { it1 -> departmentSet.add(it1) }
                    it.district?.let { it1 -> districtSet.add(it1) }
                    it.bloodgroup?.let { it1 -> bloodGroupSet.add(it1) }
                    professionSet.add(it.occupation ?: "no_data")
                }


            }

            departmentSet.forEach {
                Log.i("TAG", "setDataToRoom: $it")

            }


        }


    }*/


}




