package org.dufa.dufa9596.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.dufa.dufa9596.BaseFragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.data.models.fund_collection.RequestFundCollection
import org.dufa.dufa9596.databinding.FragmentFundCollectionBinding
import org.dufa.dufa9596.utils.NetworkResult
import org.dufa.dufa9596.utils.hide
import org.dufa.dufa9596.utils.show
import org.dufa.dufa9596.utils.showDialog
import org.dufa.dufa9596.utils.toast
import org.dufa.dufa9596.viewmodel.DashboardViewModel


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

        val dialog = Dialog(requireActivity())

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_edit_text)

        dialog.findViewById<AppCompatButton>(R.id.continuePayment).setOnClickListener {

            val editText = dialog.findViewById<EditText>(R.id.editText)
            if (editText.text.toString().isNotEmpty()) {
                sendDialogDataToActivity(fundType, editText.text.toString())
                dialog.dismiss()
            } else {
                toast("Please Enter the Valid Amount")
            }

        }

        dialog.findViewById<TextView>(R.id.cancelBtn).setOnClickListener {
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
        dashboardViewModel.responseFundCollectionVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.hide()
            when (it) {

                is NetworkResult.Error -> {
                    showDialog(
                        context = requireContext(),
                        title = "",
                        details = "${it.message}",
                        resId = R.drawable.ic_round_warning_24,
                        yesContent = "Okay",
                        noContent = "Cancel",
                        showNoBtn = false,
                        positiveFun = {
                        }, {}
                    )
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.show()

                }

                is NetworkResult.Success -> {
                    bundle.putString("paymentUrl", it.data.toString())
                    Log.e("TAG", "fundPaymentUrl: ${it.data.toString()}")
                    findNavController().navigate(
                        R.id.action_fundCollectionFragment_to_SSLFragment,
                        bundle
                    )


                }

            }

        }
    }


}