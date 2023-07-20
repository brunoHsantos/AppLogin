package br.com.dev.applogin.view.createTask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dev.applogin.model.dto.Task
import br.com.dev.applogin.model.repository.IRepositoryLocal


class FormTaskViewModel(

    val repositoryLocal: IRepositoryLocal

): ViewModel(){

    val requiredField = MutableLiveData<String>()


    fun createTask(owner: String?, title: String?, description: String?, dateLimit: Long){
        if(owner != null && owner.isNotEmpty()){
            val task = Task(owner = owner, title = title, description = description, dateLimit = dateLimit)
            repositoryLocal.insertTask(task)
        }else{
            requiredField.postValue("Campo Obrigátorio!")
        }
    }
}