package br.com.dev.applogin.remoteData

import br.com.dev.applogin.model.dto.*
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


//    @Multipart
//    @POST("pessoa/photo")
//    @Headers("Content-Type: multipart/from-data")
//    fun uploadPhoto(
//        @Part("userId") userId: RequestBody,
//        @Part photo: MultipartBody.Part
//    ): Call<PhotoProfile>
//
//
//    @POST("task")
//    fun createTask(@Body task: Task): Call<Void>
//
//    @GET("tasks")
//    fun getTasks(): Call<List<Task>>

}