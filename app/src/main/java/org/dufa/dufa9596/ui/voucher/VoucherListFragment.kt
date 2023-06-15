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
import org.dufa.dufa9596.utils.show
import org.dufa.dufa9596.utils.showDialog

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
        viewModel.voucherListVM()
        adapter = VoucherAdapter()
        binding.voucherRecyclerView.adapter = adapter
        viewModel.voucherListVMLD.observe(viewLifecycleOwner) {
            binding.progress.show()
            when (it) {
                is NetworkResult.Error -> {
                    showDialog(
                        context = requireContext(),
                        title = "",
                        details = "${it.message}",
                        resId = R.drawable.ic_close,
                        yesContent = "Okay",
                        noContent = "Cancel",
                        showNoBtn = false,
                        positiveFun = {
                        }, {}
                    )
                }
                is NetworkResult.Loading -> {
                    binding.progress.show()
                }

                is NetworkResult.Success -> {
                    adapter.submitList(it.data?.vouchers)

                }
            }


        }


    }

}