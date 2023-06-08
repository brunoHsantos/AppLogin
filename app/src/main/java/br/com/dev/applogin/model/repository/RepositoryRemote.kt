package br.com.dev.applogin.model.repository

import br.com.dev.applogin.model.dto.IdProfile
import br.com.dev.applogin.model.dto.LoginResponse
import br.com.dev.applogin.model.dto.ProfileDetail
import br.com.dev.applogin.model.dto.Usuario
import br.com.dev.applogin.remoteData.IApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryRemote(val api: IApi): IRepositoryRemote {

    override fun createProfile(profile: Usuario, isSuccess: () -> Unit, isError: () -> Unit) {
        val call = api.createProfile(profile)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                when (response.code()) {
                    in 200..202 -> {
                        isSuccess.invoke()
                    } 204 -> {
                    isSuccess.invoke()
                    }
                    else -> {
                        isError.invoke()
                    }
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                isError.invoke()
            }
        })
    }


    override fun enterLogin(login: LoginResponse, isSuccess: (IdProfile?) -> Unit, isError: () -> Unit) {
        val call = api.login(login)
        call.enqueue(object : Callback<IdProfile> {
            override fun onResponse(call: Call<IdProfile>, response: Response<IdProfile>) {
              when (response.code()){
                    in 200..202 ->{
                       isSuccess.invoke(response.body())
                    }204 -> {
                  isSuccess.invoke(null)
                    }
                    else ->{
                        isError.invoke()
                    }
                }
            }
            override fun onFailure(call: Call<IdProfile>, t: Throwable) {
                isError.invoke()
            }
        })
    }


    override fun getProfileById(_id: String, isLoading: (Boolean) -> Unit, isSuccess: (ProfileDetail?) -> Unit, isError: () -> Unit) {
        isLoading.invoke(true)
        val call = api.getProfileById(_id)
        call.enqueue(object : Callback<ProfileDetail> {
            override fun onResponse(call: Call<ProfileDetail>, response: Response<ProfileDetail>) {
                when(response.code()){
                    in 200..202 ->{
                        isSuccess.invoke(response.body())
                    }
                    204->{
                        isSuccess.invoke(null)
                    }
                    else->{
                        isError.invoke()
                    }
                }
                isLoading.invoke(false)
            }

            override fun onFailure(call: Call<ProfileDetail>, t: Throwable) {
                isError.invoke()
                isLoading.invoke(false)
            }
        })
    }
}