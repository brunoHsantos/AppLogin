package br.com.dev.applogin.view.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dev.applogin.model.dto.Usuario
import br.com.dev.applogin.model.repository.IRepositoryRemote

class RegisterViewModel(

    private val repositoryRemote: IRepositoryRemote

): ViewModel() {

    val requiredField = MutableLiveData<String>()
    val createProfileResponse = MutableLiveData<Boolean>()

    fun createProfile(name: String?, age: Int?, sex: String?, email: String?, password: String?){
        if (name != null && name.isNotEmpty()){
            val profile = Usuario( nome = name, idade= age, sexo = sex, email = email, senha = password)
           repositoryRemote.createProfile(profile,
               isSuccess = {createProfileResponse.postValue(true)},
               isError = {createProfileResponse.postValue(false)})
        }else{
            requiredField.postValue("Campos obrigatórios!")
        }
    }
}
