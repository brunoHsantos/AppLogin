package br.com.dev.applogin.model.repository

import br.com.dev.applogin.model.dataClass.Profile
import br.com.dev.applogin.remoteData.IApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryRemote(val api: IApi): IRepositoryRemote {


    override fun createProfile(profile: Profile, isSuccess: (Profile?) -> Unit, isError: () -> Unit) {
        val call = api.login(profile)
        call.enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                when (response.code()) {
                    in 200..202 -> {
                        isSuccess.invoke(response.body())
                    }
                    else -> {
                        isError.invoke()
                    }
                }
            }
            override fun onFailure(call: Call<Profile>, t: Throwable) {
                isError.invoke()
            }
        })
    }


    override fun getProfileById(profileId: Int?, isSuccess: (Profile?) -> Unit, isError: () -> Unit) {

        val call = api.getProfileById(profileId)
        call.enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                when (response.code()) {
                    in 200..202 -> {
                        isSuccess.invoke(response.body())
                    }
                    204 -> {
                        isSuccess.invoke(response.body())
                    }
                    else -> {
                        isError.invoke()
                    }
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                isError.invoke()
            }
        })
    }
}