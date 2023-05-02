package br.com.dev.applogin.remoteData

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    fun getService() = Retrofit.Builder()
        .baseUrl("https://zjq16hx7s6.execute-api.us-east-1.amazonaws.com/dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
