package com.marufalam.dufa.utils

import com.bumptech.glide.load.model.LazyHeaders


object GlideUtils {
    fun glideHeaders(authToken: String): LazyHeaders {
        return LazyHeaders.Builder()
            .addHeader("token", "Bearer $authToken")
            .build()
    }
}