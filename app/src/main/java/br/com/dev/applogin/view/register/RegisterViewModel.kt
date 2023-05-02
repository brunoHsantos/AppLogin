package br.com.dev.applogin.view.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dev.applogin.model.dataClass.Profile
import br.com.dev.applogin.model.repository.IRepositoryLocal

class RegisterViewModel(

    private val repositoryLocal: IRepositoryLocal

): ViewModel() {


    val requiredField = MutableLiveData<String>()

    fun createProfile(name: String?, age: Int?, sex: String?, email: String?, password: String?){

        if (name != null && name.isNotEmpty()){

            val profile = Profile(name = name, sex = sex, email = email, password = password)
            repositoryLocal.insertPorfile(profile)

        }else{
            requiredField.postValue("Campo Obrigatório!")
        }
    }
}
