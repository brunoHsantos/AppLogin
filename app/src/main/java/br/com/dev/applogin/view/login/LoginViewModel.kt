package br.com.dev.applogin.view.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dev.applogin.model.dataClass.Profile
import br.com.dev.applogin.model.repository.IRepositoryLocal
import br.com.dev.applogin.model.repository.IRepositoryRemote
import br.com.dev.applogin.view.detail.ProfileDetailActivity

class LoginViewModel(

    val repositoryLocal: IRepositoryLocal

): ViewModel() {

    val profileLogin = MutableLiveData<Profile>()
    val loginFailed = MutableLiveData<String>()
    val profileError = MutableLiveData<Unit>()



    /*fun loginProfileId(profile: Int?){ repositoryRemote.getProfileById( profile, { profile->
        profile?.let {
            profileLogin.postValue(it)
        }?: run{
            profileError.postValue(Unit)
        }
    },
        isError = {
            profileError.postValue(Unit)
        }

    )

    }

     */

    fun loginProfile(profile: Int?) {

        val profile = repositoryLocal.profileById(profile)
        profile?.let { profileLogin.postValue(it) }

        loginFailed.postValue("Campos vazios")
    }

}









