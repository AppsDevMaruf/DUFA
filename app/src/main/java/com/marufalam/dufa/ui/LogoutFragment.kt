package com.marufalam.dufa.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.data.local.TokenManager
import com.marufalam.dufa.databinding.FragmentLogoutBinding
import com.marufalam.dufa.utils.Constants
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.toast
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LogoutFragment : DialogFragment() {
    private val dashboardViewModel by viewModels<DashboardViewModel>()

    lateinit var binding: FragmentLogoutBinding

    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogoutBinding.inflate(inflater, container, false)

        setupNavigation()
        binObserver()


        return binding.root
    }


    fun setupNavigation() {

        binding.notLogout.setOnClickListener {

            findNavController().popBackStack()
        }

        binding.yesLogout.setOnClickListener {
            dashboardViewModel.logoutVM()
        }


    }

    fun binObserver() {
        dashboardViewModel.logoutVMLD.observe(this) {
            //progressBar.isVisible = false
            when (it) {

                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {
                    // progressBar.isVisible = true

                }
                is NetworkResult.Success -> {
                    tokenManager.saveToken(Constants.TOKEN, Constants.NO_DATA)

                    toast(it.data!!.message)
                    Log.i("logout", "DashboardSuccess: ${it.data!!.message}")

                    requireActivity().run {
                        startActivity(
                            Intent(
                                requireContext(),
                                LogInActivity::class.java
                            )
                        )

                        finish()
                    }


                }


            }

        }
    }

//    override fun onResume() {
//        super.onResume()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
//    }

}