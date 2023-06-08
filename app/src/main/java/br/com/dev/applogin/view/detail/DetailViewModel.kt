package br.com.dev.applogin.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dev.applogin.model.dto.IdProfile
import br.com.dev.applogin.model.dto.ProfileDetail
import br.com.dev.applogin.model.repository.IRepositoryRemote

class DetailViewModel(

   private val repositoryRemote: IRepositoryRemote


): ViewModel(){

    val isLoading = MutableLiveData(false)
    val profileDetail = MutableLiveData<ProfileDetail>()
    val profileError = MutableLiveData<Unit>()


    fun profileById(id: String){
        repositoryRemote.getProfileById(id, isSuccess = { profiledetail->
            profiledetail?.let {
                profileDetail.postValue(it)
            }?: run {profileError.postValue(Unit)}
            }, isError = {
            profileError.postValue(Unit)
            }, isLoading = {
            isLoading.postValue(it)
            }
        )
    }
}