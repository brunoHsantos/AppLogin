package br.com.dev.applogin.view.tasks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dev.applogin.model.dto.Task
import br.com.dev.applogin.model.repository.IRepositoryLocal

class TaskViewModel(

    private val repositoryLocal: IRepositoryLocal

): ViewModel() {

    val isLoading = MutableLiveData(false)
    val taskLocal = MutableLiveData<List<Task>?>()
    val taskRemoteError = MutableLiveData<Unit>()

    fun getTask(){
        taskLocal.postValue(repositoryLocal.getAllTasks())
    }

    fun deleteTask(task: Task) {
        repositoryLocal.deleteTask(task)
    }

    fun updateTask(task: Task) {
        repositoryLocal.updateTask(task)
    }
}
