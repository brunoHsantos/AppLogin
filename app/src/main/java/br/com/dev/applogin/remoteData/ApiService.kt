package br.com.dev.applogin.remoteData

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()

    fun getService() = Retrofit.Builder()
        .baseUrl("https://zjq16hx7s6.execute-api.us-east-1.amazonaws.com/dev/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
