package org.dufa.dufa9596.utils

import com.bumptech.glide.load.model.LazyHeaders


object GlideUtils {
    fun glideHeaders(authToken: String): LazyHeaders {
        return LazyHeaders.Builder()
            .addHeader("token", "Bearer $authToken")
            .build()
    }
}