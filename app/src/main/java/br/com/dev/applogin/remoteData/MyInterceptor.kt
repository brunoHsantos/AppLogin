package br.com.dev.applogin.remoteData

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class MyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("x-api-key", "pfUKthW2BWNhghsgNpAb8cpFckD5yGsaDFwomoDa")
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}