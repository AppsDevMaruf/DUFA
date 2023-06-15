package org.dufa.dufa9596.ui.trans_history


import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.dufa.dufa9596.BaseFragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.databinding.FragmentTransactionHistoryBinding
import org.dufa.dufa9596.utils.NetworkResult

@AndroidEntryPoint
class TransactionHistoryFragment : BaseFragment<FragmentTransactionHistoryBinding>() {
    private val viewModel by viewModels<TransactionHistoryViewModel>()
    lateinit var adapter: TransHistoryAdapter

    override fun getFragmentView(): Int {
        return R.layout.fragment_transaction_history
    }
    override fun configUi() {

    }



    override fun binObserver() {
        viewModel.transactionHistoryVM()
        adapter = TransHistoryAdapter()
        binding.transHistoryRcv.adapter = adapter
        viewModel.transactionHistoryVMLD.observe(viewLifecycleOwner) {
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