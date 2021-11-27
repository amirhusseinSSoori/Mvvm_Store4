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