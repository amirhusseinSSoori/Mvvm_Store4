package com.example.myapplication.data.network

import com.example.myapplication.common.Constance
import com.example.myapplication.common.Constance.TOKEN
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