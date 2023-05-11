package com.marufalam.dufa.ui.voucher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.adapter.VoucherAdapter
import com.marufalam.dufa.databinding.FragmentVoucherListBinding
import com.marufalam.dufa.ui.trans_history.TransHistoryAdapter
import com.marufalam.dufa.ui.trans_history.TransactionHistoryViewModel
import com.marufalam.dufa.ui.voucher.VoucherViewModel
import com.marufalam.dufa.utils.NetworkResult
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