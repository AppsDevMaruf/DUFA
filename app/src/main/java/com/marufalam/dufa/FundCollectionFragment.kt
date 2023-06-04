package com.marufalam.dufa

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.data.models.fund_collection.RequestFundCollection
import com.marufalam.dufa.databinding.FragmentFundCollectionBinding
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.utils.toast
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FundCollectionFragment : BaseFragment<FragmentFundCollectionBinding>() {
    private lateinit var fund: RequestFundCollection
    private var bundle = Bundle()
    private val dashboardViewModel by viewModels<DashboardViewModel>()
    override fun getFragmentView(): Int {
        return R.layout.fragment_fund_collection

    }

    override fun setupNavigation() {
        binding.generalFund.setOnClickListener {
            showAlertDialogButtonClicked("general_fund")
        }
        binding.welfareFund.setOnClickListener {
            showAlertDialogButtonClicked("welfare_fund")
        }
        binding.zakatFund.setOnClickListener {
            showAlertDialogButtonClicked("zakat_fund")
        }
    }

    private fun showAlertDialogButtonClicked(fundType: String) {
        // Create an alert builder
        val builder = AlertDialog.Builder(requireActivity())

        // set the custom layout
        val customLayout: View = layoutInflater.inflate(R.layout.alert_dialog_with_edittext, null)
        builder.setView(customLayout)

        val dialog = builder.create()
        val continuePaymentBtn = customLayout.findViewById<AppCompatButton>(R.id.continuePayment)
        continuePaymentBtn.setOnClickListener {

            val editText = customLayout.findViewById<EditText>(R.id.editText)
            if (editText.text.toString().isNotEmpty()){
                sendDialogDataToActivity(fundType, editText.text.toString())
                dialog.dismiss()
            }else{
                toast("Please Enter the Valid Amount")
            }

        }

        val cancelBtn = customLayout.findViewById<ImageView>(R.id.cancelBtn)
        cancelBtn.setOnClickListener {
            dialog.dismiss()

        }
        dialog.show()
    }

    // Do something with the data coming from the AlertDialog
    private fun sendDialogDataToActivity(fundType: String, data: String) {


        when (fundType) {
            "welfare_fund" -> {
                fund = RequestFundCollection(
                    generalFund = null,
                    type = "other_fee",
                    welfareFund = data.toDouble(),
                    zakatFund = null
                )
            }
            "general_fund" -> fund = RequestFundCollection(
                generalFund = data.toDouble(),
                type = "other_fee",
                welfareFund = null,
                zakatFund = null
            )
            "zakat_fund" -> {
                fund = RequestFundCollection(
                    generalFund = null,
                    type = "other_fee",
                    welfareFund = null,
                    zakatFund = data.toDouble()
                )
            }
        }
        dashboardViewModel.fundCollectionVM(fund)

    }

    override fun binObserver() {
        dashboardViewModel.responseFundCollectionVMLD.observe(this) {

            when (it) {

                is NetworkResult.Error -> {
                    Log.i("Error", "NetworkResult.Error: ${it.data.toString()}")
                }

                is NetworkResult.Loading -> {
                    // progressBar.isVisible = true

                }
                is NetworkResult.Success -> {
                    bundle.putString("fundPaymentUrl",it.data.toString())

                    findNavController().navigate(R.id.action_fundCollectionFragment_to_SSLFragment,bundle)


                }

            }

        }
    }


}