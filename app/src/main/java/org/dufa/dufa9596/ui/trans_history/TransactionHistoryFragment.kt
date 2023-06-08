package org.dufa.dufa9596.ui.trans_history


import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import org.dufa.dufa9596.BaseFragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.databinding.FragmentTransactionHistoryBinding
import org.dufa.dufa9596.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFragment : BaseFragment<FragmentTransactionHistoryBinding>() {
    private val viewModel by viewModels<TransactionHistoryViewModel>()
    lateinit var adapter: TransHistoryAdapter

    override fun getFragmentView(): Int {
        return R.layout.fragment_transaction_history
    }
    override fun configUi() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigateUp() // to clear previous navigation history
            findNavController().navigate(R.id.action_transactionHistoryFragment_to_DashboardFragment)

        }

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