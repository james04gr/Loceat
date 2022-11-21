package com.xecoding.loceat.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            addHeader("Content-Type", "application/json;charset=utf-8")
            addHeader("Accept", "application/json;charset=utf-8")
        }.build()
        return chain.proceed(request)
    }
}
