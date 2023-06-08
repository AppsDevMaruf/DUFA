package org.dufa.dufa9596.ui.voucher

import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import org.dufa.dufa9596.BaseFragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.adapter.VoucherAdapter
import org.dufa.dufa9596.databinding.FragmentVoucherListBinding
import org.dufa.dufa9596.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VoucherListFragment : BaseFragment<FragmentVoucherListBinding>() {


    private val viewModel by viewModels<VoucherViewModel>()

    lateinit var adapter: VoucherAdapter

    override fun getFragmentView(): Int {
        return R.layout.fragment_voucher_list
    }

    override fun configUi() {
        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_voucherListFragment_to_voucherFragment)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigateUp()
            findNavController().navigate(R.id.action_voucherListFragment_to_DashboardFragment)
        }
    }


    override fun binObserver() {
        viewModel.getVoucherListVM()
        adapter = VoucherAdapter()
        binding.voucherRecyclerView.adapter = adapter
        viewModel.getVoucherListVMLD.observe(viewLifecycleOwner) {


            when (it) {
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    adapter.submitList(it.data?.vouchers)

                }
            }


        }


    }

}