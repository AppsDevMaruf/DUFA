package com.marufalam.dufa.ui

import android.util.Log
import com.example.awesomedialog.*
import com.marufalam.dufa.R
import com.marufalam.dufa.databinding.FragmentPaymentBinding
import com.marufalam.dufa.BaseFragment
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCAdditionalInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentPaymentBinding>(),SSLCTransactionResponseListener{

    private var sslCommerzInitialization: SSLCommerzInitialization? = null
    var additionalInitializer: SSLCAdditionalInitializer? = null



    override fun getFragmentView(): Int {
        return R.layout.fragment_payment
    }

    override fun configUi() {
        binding.registerCard.setOnClickListener {
            sslSetUp(1000)
        }
//        binding.poneTimeFee.setOnClickListener {
//            sslSetUp(3000)
//        }
//        binding.yearlePaymentsCard.setOnClickListener {
//            sslSetUp(5000)
//        }

    }

    override fun setupNavigation() {

    }

    override fun binObserver() {

    }

    private fun sslSetUp(amount: Int) {

        val currentTimestamp = System.currentTimeMillis()

        //ssl_commarz
        sslCommerzInitialization = SSLCommerzInitialization(
            "dufa9596live",
            "dufa9596live51112",
            amount.toDouble(), SSLCCurrencyType.BDT,
            "MYID$currentTimestamp",
            "yourProductType",
            SSLCSdkType.LIVE
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
}
