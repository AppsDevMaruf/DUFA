package org.dufa.dufa9596.ui

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.*
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import org.dufa.dufa9596.BaseFragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.data.models.payRenew.RequestPayRenew
import org.dufa.dufa9596.databinding.FragmentDuesBinding
import org.dufa.dufa9596.utils.NetworkResult
import org.dufa.dufa9596.utils.gone
import org.dufa.dufa9596.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DuesFragment : BaseFragment<FragmentDuesBinding>() {
    private val dashboardViewModel by viewModels<DashboardViewModel>()


    override fun getFragmentView(): Int {
     return R.layout.fragment_dues
    }

    override fun configUi() {


    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWeb(url:String) {

        binding.webView.loadUrl(url)
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {


                return super.shouldInterceptRequest(view, request)
            }

            override fun onRenderProcessGone(
                view: WebView?,
                detail: RenderProcessGoneDetail?
            ): Boolean {
                return super.onRenderProcessGone(view, detail)
            }


            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                return if (url != null) {
                    view?.loadUrl(url)
                    true
                } else {
                    true
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {

                /* binding.webView.show()
                 binding.progress.hide()*/
                binding.button.gone()
            }
        }
        binding.webView.settings.javaScriptEnabled = true

        binding.webView.addJavascriptInterface(object {
            @JavascriptInterface
            fun sendData(string: String, string2: String) {
                var responseDwolla = ""
                responseDwolla = Gson().fromJson(string, responseDwolla.javaClass)


            }

        }, "IAVCommunicator")



    }

    override fun setupNavigation() {
        binding.button.setOnClickListener {
            val request = RequestPayRenew(
                amount = 2000,
                membership ="yearly",
                userinfoID = 1384,
                renewFee = "renew_fee"
            )
            dashboardViewModel.payRenewVM(request)
        }

    }

    override fun binObserver() {
        dashboardViewModel.responsePayRenewVMLD.observe(this) {

            when (it) {

                is NetworkResult.Error -> {
                    Log.i("Error", "NetworkResult.Error: ${it.data.toString()}")
                }

                is NetworkResult.Loading -> {
                    // progressBar.isVisible = true

                }
                is NetworkResult.Success -> {

                    binding.textView.text = it.data
                    loadWeb(it.data.toString())



                }


            }

        }

    }


}