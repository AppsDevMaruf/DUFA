package com.marufalam.dufa.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.awesomedialog.*
import com.marufalam.dufa.R
import com.marufalam.dufa.databinding.FragmentPaymentBinding
import com.marufalam.dufa.fragments.BaseFragment
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCAdditionalInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment :Fragment(),SSLCTransactionResponseListener{

    private var sslCommerzInitialization: SSLCommerzInitialization? = null
    var additionalInitializer: SSLCAdditionalInitializer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }



    private fun sslSetUp(amount: Int) {

        val currentTimestamp = System.currentTimeMillis()

        //ssl_commarz
        sslCommerzInitialization = SSLCommerzInitialization(
            "mobil5fe45035efe16",
            "mobil5fe45035efe16@ssl",
            amount.toDouble(), SSLCCurrencyType.BDT,
            "MYID$currentTimestamp",
            "yourProductType",
            SSLCSdkType.TESTBOX
        )

        additionalInitializer = SSLCAdditionalInitializer()
        additionalInitializer!!.valueA = "Value Option 1"
        additionalInitializer!!.valueB = "Value Option 1"
        additionalInitializer!!.valueC = "Value Option 1"
        additionalInitializer!!.valueD = "Value Option 1"




        IntegrateSSLCommerz
            .getInstance(activity)
            .addSSLCommerzInitialization(sslCommerzInitialization)
            .addAdditionalInitializer(additionalInitializer)
            .buildApiCall(this)


    }
    override fun transactionSuccess(successInfo: SSLCTransactionInfoModel?) {
        if (successInfo != null) {


            Log.d("BODYDATA", successInfo.tranId)
            Log.d("BODYDATA", successInfo.status)

            AwesomeDialog
                .build(requireActivity())
                .position(AwesomeDialog.POSITIONS.CENTER)
                .title("Id:${successInfo.tranId} \nAmount: ${successInfo.amount} \nPayment Status:${successInfo.apiConnect}")
                .icon(R.drawable.payment)
                .onPositive("Continue") {

                }

        }
    }

    override fun transactionFail(p0: String?) {
        AwesomeDialog
            .build(requireActivity())
            .position(AwesomeDialog.POSITIONS.CENTER)
            .title("$p0")
            .icon(R.mipmap.ic_launcher)
            .onPositive("Continue") {

            }
    }

    override fun merchantValidationError(p0: String?) {
        AwesomeDialog
            .build(requireActivity())
            .position(AwesomeDialog.POSITIONS.CENTER)
            .title("$p0")
            .icon(R.mipmap.ic_launcher)
            .onPositive("Continue") {

            }
    }

   /* override fun getFragmentView(): Int {
        return R.layout.fragment_payment
    }

    override fun configUi() {
     *//*   binding.registerCard.setOnClickListener {
            sslSetUp(1000)
        }
        binding.oneTimeFee.setOnClickListener {
            sslSetUp(3000)
        }
        binding.yearlePaymentsCard.setOnClickListener {
            sslSetUp(5000)
        }*//*

    }

    override fun setupNavigation() {

    }

    override fun binObserver() {

    }
*/

}
