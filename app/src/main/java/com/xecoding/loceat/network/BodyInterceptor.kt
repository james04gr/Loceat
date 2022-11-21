package com.xecoding.loceat.network

import com.xecoding.loceat.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class BodyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("client_id", BuildConfig.CLIENT_ID)
            .addQueryParameter("client_secret", BuildConfig.CLIENT_SECRET)
            .addQueryParameter("v", BuildConfig.V)
            .addQueryParameter("limit", BuildConfig.LIMIT)
            .addQueryParameter("categoryId", BuildConfig.CATEGORY_ID)
            .build()

        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}