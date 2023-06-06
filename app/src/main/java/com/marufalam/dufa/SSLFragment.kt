package com.marufalam.dufa

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.marufalam.dufa.databinding.FragmentSSLBinding
import com.marufalam.dufa.utils.gone
import com.marufalam.dufa.utils.show
import com.marufalam.dufa.utils.toast
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SSLFragment : BaseFragment<FragmentSSLBinding>() {
    private var paymentUrl: String? = null
    override fun getFragmentView(): Int {
        return R.layout.fragment_s_s_l
    }

    override fun configUi() {
        if (arguments != null) {
            paymentUrl = requireArguments().getString("paymentUrl")!!
            loadWeb(paymentUrl!!)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWeb(url: String) {
        binding.webView.apply {
            val layerType =
                if (Build.VERSION.SDK_INT >= 19) View.LAYER_TYPE_HARDWARE else View.LAYER_TYPE_SOFTWARE
            setLayerType(layerType, null)
        }
        binding.webView.loadUrl(url)
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {

                return super.shouldInterceptRequest(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                binding.progress.show()

                Log.i("TAG", "onPageStarted: $url")

                super.onPageStarted(view, url, favicon)
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

                binding.progress.gone()

                Log.i("TAG", "onPageFinished: $url")

                if (url == "http://dufa9596.org/success") {

                    findNavController().navigate(R.id.payentSuccessFragment)
                    toast("success")


                }

                if (url == "http://dufa9596.org/fail") {

                    findNavController().navigate(R.id.paymentFailledFragment)

                    toast("failed")

                }


                if (url == "http://dufa9596.org/cancel") {

                    findNavController().navigate(R.id.paymentFailledFragment)

                    toast("canceled")
                }


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

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }


}