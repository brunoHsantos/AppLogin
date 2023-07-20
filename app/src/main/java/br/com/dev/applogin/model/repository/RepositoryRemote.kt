package br.com.dev.applogin.model.repository

import br.com.dev.applogin.model.dto.*
import br.com.dev.applogin.remoteData.IApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*Classe responsável por fazer a consulta na api. Portanto, criamos como parâmetro uma variável que vai instanciar
justamente a `IApi`, exercendo a mesma função do `IRepositoryRemote`, uma interface com funcões esperando valores para serem tratados.
 */
class RepositoryRemote(val api: IApi): IRepositoryRemote {


    //Usando nossas funcões da interface `IRepositoryRemote`, declaramos retornos a elas. utilizando do CallBack

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
                  isError.invoke()
                    }
                }
            }
            override fun onFailure(call: Call<IdProfile>, t: Throwable) {
                isError.invoke()
            }
        })
    }


    override fun getProfileById(isLoading: (Boolean) -> Unit, _id: String, isSuccess: (ProfileDetail?) -> Unit, isError: () -> Unit) {
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