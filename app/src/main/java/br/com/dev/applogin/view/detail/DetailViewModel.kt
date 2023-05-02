package br.com.dev.applogin.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dev.applogin.model.dataClass.Profile
import br.com.dev.applogin.model.repository.IRepositoryRemote

class DetailViewModel(

   private val repositoryRemote: IRepositoryRemote

): ViewModel(){

    val profileDetail = MutableLiveData<Profile>()
    val profileError = MutableLiveData<Unit>()

    fun profileById(profileId: Int){ repositoryRemote.getProfileById( profileId, { profile->
            profile?.let {
                profileDetail.postValue(it)
            }?: run{
                profileError.postValue(Unit)
            }
        },
            isError = {
                profileError.postValue(Unit)
            }

        )
    }
}