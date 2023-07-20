package br.com.dev.applogin.model.repository

import br.com.dev.applogin.localData.TaskDatabase
import br.com.dev.applogin.model.dto.Task
import br.com.dev.applogin.model.dto.UserProfile


class RepositoryLocal(private val taskDatabase: TaskDatabase? = null): IRepositoryLocal {

    override fun insertTask(task: Task) {
        taskDatabase?.taskDao()?.insert(task)
    }

    override fun getAllTasks(): List<Task>? {
        return taskDatabase?.taskDao()?.getAllTasks()
    }

    override fun deleteTask(task: Task) {
        taskDatabase?.taskDao()?.delete(task)
    }

    override fun getTaskById(taskId: Int): Task? {
       return taskDatabase?.taskDao()?.getTaskById(taskId)
    }

    override fun updateTask(task: Task) {
        taskDatabase?.taskDao()?.update(task)
    }

    override fun insertPhoto(photo: UserProfile) {
        taskDatabase?.taskDao()?.insertPhotoProfile(photo)
    }

    override fun updatePhoto(updatePhoto: UserProfile) {
        taskDatabase?.taskDao()?.updatePhotoProfile(updatePhoto)
    }
}