package br.com.dev.applogin.view.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dev.applogin.model.dto.IdProfile
import br.com.dev.applogin.model.dto.LoginResponse
import br.com.dev.applogin.model.repository.IRepositoryRemote


class LoginViewModel(

    val repositoryRemote: IRepositoryRemote

): ViewModel() {


    val profileLoginRemoteId = MutableLiveData<IdProfile?>()
    val loginError = MutableLiveData<Unit>()
    val requiredField = MutableLiveData<String>()


  fun getProfileRemote(login: LoginResponse){
       if (login.email != null && login.email.isNotEmpty()){
           repositoryRemote.enterLogin(login, isSuccess ={profileLoginRemoteId.postValue(it)}, isError = {loginError.postValue(Unit)})
       }else{
           requiredField.postValue("Campos Obrigatórios!")
       }
    }
}










