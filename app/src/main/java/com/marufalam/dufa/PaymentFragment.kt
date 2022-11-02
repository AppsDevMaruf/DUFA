package com.marufalam.dufa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import com.example.awesomedialog.*
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCAdditionalInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener


class PaymentFragment : Fragment(),SSLCTransactionResponseListener,AdapterView.OnItemSelectedListener{
    lateinit var eTAmount: EditText
    lateinit var showprice: TextView
    lateinit var payButton: Button

    private var sslCommerzInitialization: SSLCommerzInitialization? = null
    var additionalInitializer: SSLCAdditionalInitializer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_payment, container, false)
        // get reference to the string array that we just created
        val paymentType = resources.getStringArray(R.array.paymentsType)
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.dropdown_item, paymentType)
        // get reference to the autocomplete text view
        val spinner = view.findViewById<AppCompatSpinner>(R.id.payment_spinner)
        spinner.onItemSelectedListener = this
        // set adapter to the autocomplete tv to the arrayAdapter
        spinner.setAdapter(arrayAdapter)



        payButton=view.findViewById(R.id.payNowButtonId)
        showprice=view.findViewById(R.id.priceShow)


        payButton.setOnClickListener {
            val amount=showprice.text.toString().trim()
            if (amount.isNotEmpty()){
                sslSetUp(amount)
            }else{
                eTAmount.error="Error"
            }
        }
        return view;
    }
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, possition: Int, p3: Long) {
        when(possition){
            0->showprice!!.text = "1000"
            1->showprice!!.text = "3000"
            2->showprice!!.text = "5000"
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun sslSetUp(amount:String) {

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


}
