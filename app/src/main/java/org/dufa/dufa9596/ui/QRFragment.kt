package org.dufa.dufa9596.ui

import android.graphics.Color
import android.util.Log
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import org.dufa.dufa9596.BaseFragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.databinding.FragmentQRBinding
import org.dufa.dufa9596.utils.NetworkResult
import org.dufa.dufa9596.viewmodel.DashboardViewModel
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
                    val address = " DEPT: ${it.data?.memberInfo?.department}\n Email:" +
                            " ${it.data?.memberInfo?.email}\n  Phone:" +
                            " ${it.data?.memberInfo?.phone}"

                    binding.userName.text = name
                    binding.userAdress.text = address

                    if (it.data?.memberInfo?.subscription == "none") {
                        val text =
                            "<font color=#4d4d4d>Status: </font> <font color=#EB5757>Inactive</font>"
                        binding.status.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);
                    } else {
                        val text =
                            "<font color=#4d4d4d>Status: </font> <font color=#00AA0E>Active</font>"
                        binding.status.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    }


                    profileQr = "$name \n $address"

                    qrGenerate(profileQr)


                }


            }

        }

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