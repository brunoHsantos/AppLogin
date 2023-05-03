package br.com.dev.applogin.remoteData

import br.com.dev.applogin.model.dataClass.Profile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IApi {

    @GET("profile/detail")
    fun getProfileById(@Query("profileId") profileId: Int?): Call<Profile>


    @POST("login")
    fun login(@Query("login") profile: Profile): Call<Profile>


}