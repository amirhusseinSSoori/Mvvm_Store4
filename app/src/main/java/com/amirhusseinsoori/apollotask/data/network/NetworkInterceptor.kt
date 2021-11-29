package com.amirhusseinsoori.apollotask.data.network

import com.amirhusseinsoori.apollotask.common.Constance.TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        return chain!!.proceed(
            chain.request().newBuilder()
                .header("Authorization", "Bearer $TOKEN")
                .build()
        )
    }
}