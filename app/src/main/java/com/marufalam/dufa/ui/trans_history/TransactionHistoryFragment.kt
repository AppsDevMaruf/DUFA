package com.marufalam.dufa.ui.trans_history


import androidx.fragment.app.viewModels
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.databinding.FragmentTransactionHistoryBinding
import com.marufalam.dufa.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFragment : BaseFragment<FragmentTransactionHistoryBinding>() {


    private val viewModel by viewModels<TransationHistoryViewModel>()


    lateinit var adapter: TransHistoryAdapter


    override fun getFragmentView(): Int {
        return R.layout.fragment_transaction_history
    }

    override fun binObserver() {
        viewModel.getTransactionHistoryVM()
        adapter = TransHistoryAdapter()
        binding.transHistoryRcv.adapter = adapter
        viewModel.getTransactionHistoryVMLD.observe(viewLifecycleOwner) {


            when (it) {
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    adapter.submitList(it.data?.orders)

                }
            }


        }


    }


}