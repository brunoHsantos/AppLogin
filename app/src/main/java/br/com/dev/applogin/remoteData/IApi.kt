package br.com.dev.applogin.remoteData

import br.com.dev.applogin.model.dto.IdProfile
import br.com.dev.applogin.model.dto.LoginResponse
import br.com.dev.applogin.model.dto.ProfileDetail
import br.com.dev.applogin.model.dto.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IApi {

    @GET("pessoa?id=")
    fun getProfileById(@Query("id") id: String): Call<ProfileDetail>

    @POST("login")
    fun login(@Body login: LoginResponse): Call<IdProfile>

    @POST("pessoa")
    fun createProfile(@Body profile: Usuario): Call<Void>

}