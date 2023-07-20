package br.com.dev.applogin.view.detailProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dev.applogin.model.dto.ProfileDetail
import br.com.dev.applogin.model.dto.UserProfile
import br.com.dev.applogin.model.repository.IRepositoryLocal
import br.com.dev.applogin.model.repository.IRepositoryRemote
import br.com.dev.applogin.model.repository.RepositoryLocal

class DetailViewModel(

   private val repositoryRemote: IRepositoryRemote,
   private val repositoryLocal: IRepositoryLocal


): ViewModel(){

    val isLoading = MutableLiveData(false)
    val profileDetail = MutableLiveData<ProfileDetail>()
    val profilePhoto = MutableLiveData<UserProfile>()
    val profileError = MutableLiveData<Unit>()



    fun profileById(id: String){
        repositoryRemote.getProfileById(isLoading = {
            isLoading.postValue(it)
        }, _id = id, isSuccess = { profiledetail->
            profiledetail?.let {
                profileDetail.postValue(it)
            }?: run {profileError.postValue(Unit)}
            }, isError = {
            profileError.postValue(Unit)
            },
        )
    }

    fun updatePhoto(photo: UserProfile){
        repositoryLocal.insertPhoto(photo)
    }
}