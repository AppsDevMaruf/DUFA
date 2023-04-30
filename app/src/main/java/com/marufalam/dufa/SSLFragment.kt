package com.marufalam.dufa

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.*
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.marufalam.dufa.databinding.FragmentSSLBinding
import com.marufalam.dufa.utils.NetworkResult
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SSLFragment : BaseFragment<FragmentSSLBinding>() {


    override fun getFragmentView(): Int {
        return R.layout.fragment_s_s_l
    }


    private val dashboardViewModel by activityViewModels<DashboardViewModel>()




    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWeb(url: String) {

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

                Log.i("TAG", "onPageFinished: $url")



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


    }

    override fun binObserver() {

        dashboardViewModel.paymentUrl.observe(viewLifecycleOwner) {


            loadWeb(it)

        }




    }


}