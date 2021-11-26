package com.example.myapplication.data.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        return chain!!.proceed(
            chain.request().newBuilder()
                .header("Authorization", "Bearer " + "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")
                .build()
        )
    }
}