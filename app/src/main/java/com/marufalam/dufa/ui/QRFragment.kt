package com.marufalam.dufa.ui

import android.graphics.Color
import android.util.Log
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.activityViewModels
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.databinding.FragmentQRBinding
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QRFragment : BaseFragment<FragmentQRBinding>() {
    private val dashboardViewModel by activityViewModels<DashboardViewModel>()

    var profileQr = ""


    override fun getFragmentView(): Int {

        return R.layout.fragment_q_r
    }


    override fun configUi() {


    }


    override fun binObserver() {


        dashboardViewModel.dashboardInfoVMLD.observe(viewLifecycleOwner) {

            when (it) {

                is NetworkResult.Error -> {

                    //Log.i("TAG1", "binObserver: ${it.data!!.message.toString()}")
                }
                is NetworkResult.Loading -> {


                }
                is NetworkResult.Success -> {

                    val name = it.data?.memberInfo?.name
                    val address = " DEPT:\n ${it.data?.memberInfo?.department}\n Email:" +
                            " ${it.data?.memberInfo?.email}\n  Phone:" +
                            " ${it.data?.memberInfo?.phone}\n Status: " +
                            " ${it.data?.memberInfo?.status}   "

                    binding.userName.text = name
                    binding.userAdress.text = address


                    profileQr = "$name \n $address"



                    qrGenerate(profileQr)


                }


            }

        }

        //

//        dashboardViewModel.profileInfoVMLD.observe(viewLifecycleOwner) {
//
//            when (it) {
//                is NetworkResult.Error -> TODO()
//                is NetworkResult.Loading -> TODO()
//                is NetworkResult.Success -> {
//
//
//                    val name = it.data?.name
//                    val address = it.data?.address
//
//                    binding.userName.text = name
//                    binding.userAdress.text = address
//
//
//                    profileQr = "$name \n $address"
//
//
//
//                    qrGenerate(profileQr)
//
//
//                }
//            }
//
//
//        }


    }


    private fun qrGenerate(qrTxt: String) {


        // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        val qrgEncoder = QRGEncoder(qrTxt, null, QRGContents.Type.TEXT, 500)
        qrgEncoder.colorBlack = Color.TRANSPARENT
        qrgEncoder.colorWhite = Color.BLACK
        try {
            // Getting QR-Code as Bitmap
            val bitmap = qrgEncoder.bitmap
            // Setting Bitmap to ImageView
            binding.qrView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            Log.v("TAG", e.message!!)
        }


    }

}