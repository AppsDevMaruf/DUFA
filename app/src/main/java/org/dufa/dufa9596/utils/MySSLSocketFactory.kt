package org.dufa.dufa9596.utils

import android.annotation.SuppressLint
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object MySSLSocketFactory {
    fun createSSLSocketFactory(): SSLSocketFactory {
        val trustAllCerts: Array<TrustManager> = arrayOf(MyTrustManager())
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        return sslContext.socketFactory
    }
}

@SuppressLint("CustomX509TrustManager")
class MyTrustManager : X509TrustManager {
    @SuppressLint("TrustAllX509TrustManager")
    override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
    }

    @SuppressLint("TrustAllX509TrustManager")
    override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
    }

    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
        return arrayOf()
    }
}
