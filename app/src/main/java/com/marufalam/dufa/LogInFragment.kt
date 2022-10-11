package com.marufalam.dufa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.databinding.FragmentLogInBinding
import com.marufalam.dufa.models.LoginRequest
import com.marufalam.dufa.models.LoginResponse
import com.marufalam.dufa.viewmodel.DashboardViewModel
import com.marufalam.weatherapps.networks.NetworkService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class LogInFragment : Fragment() {


    val dashboardViewModel: DashboardViewModel by activityViewModels<DashboardViewModel>()


    lateinit var binding: FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // viewModel= ViewModelProviders.of(this).get(ScoreViewModel::class.java)
        binding = FragmentLogInBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.logIn.setOnClickListener {
            var email = binding.loginEmail.text.toString().trim()
            var password = binding.loginPassword.text.toString().trim()
            val loginRequest = LoginRequest(email, password)


            var response =dashboardViewModel.login(loginRequest)

            if (response!= null){
                findNavController().navigate(R.id.action_Login_to_Dashboard)


            }






//            if (response.isSuccessful) {
//                Log.i("TAG", "Okay")
//
//
//
//            } else {
//
//                Log.i(
//                    "TAG",
//                    " ${
//                        NetworkService.dufaServiceApi.login(loginRequest).errorBody().toString()
//                    } "
//                )
            }






//            dashboardViewModel.login(loginRequest)
//
//            findNavController().navigate(R.id.action_Login_to_Dashboard)
//
//            dashboardViewModel.userLoginLiveData.observe(viewLifecycleOwner) {
//                Log.i("TAG", "lo ${it.data}")
//            }


        }


    }

//
//    override fun onResume() {
//        super.onResume()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
//    }
//}
