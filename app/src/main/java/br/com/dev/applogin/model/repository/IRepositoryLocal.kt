package br.com.dev.applogin.model.repository

import br.com.dev.applogin.model.dto.Task
import br.com.dev.applogin.model.dto.UserProfile


interface IRepositoryLocal {

    fun insertTask(task: Task) {}

    fun getAllTasks(): List<Task>?

    fun deleteTask(task: Task) {}

    fun getTaskById(taskId: Int) : Task?

    fun updateTask(task: Task) {}

    fun insertPhoto(photo: UserProfile){}

    fun  updatePhoto(updatePhoto: UserProfile){}
}