package br.com.dev.applogin.view.detailTask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dev.applogin.model.dto.Task
import br.com.dev.applogin.model.repository.IRepositoryLocal

class TaskDetailViewModel (

    val repositoryLocal: IRepositoryLocal

): ViewModel(){

    val taskObservable = MutableLiveData<Task>()
//    val updateFailed = MutableLiveData<String>()
//    val updateSuccess = MutableLiveData<Unit>()

    fun getTaskById(taskId: Int) {
        val task = repositoryLocal.getTaskById(taskId)
        task?.let { taskObservable.postValue(it) }
    }

    fun deleteTask(task: Task) {
        repositoryLocal.deleteTask(task)
    }


//    fun updateTask(taskId: Int, newTitle: String?, newDescription: String?) {
//        val task = repositoryLocal.getTaskById(taskId)
//
//        if(task != null && (task.title != newTitle || task.description != newDescription)) {
//            task.title = newTitle
//            task.description = newDescription
//            repositoryLocal.updateTask(task)
//            updateSuccess.postValue(Unit)
//        } else {
//            updateFailed.postValue("Task sem dados alterados")
//        }
//    }

}