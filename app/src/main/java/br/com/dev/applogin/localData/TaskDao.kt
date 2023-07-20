package br.com.dev.applogin.localData

import androidx.room.*
import br.com.dev.applogin.model.dto.Task
import br.com.dev.applogin.model.dto.UserProfile

@Dao
interface TaskDao {

    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Update
    fun update(task: Task)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): MutableList<Task>

    @Query("SELECT * FROM tasks WHERE id == :taskId")
    fun getTaskById(taskId: Int): Task

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotoProfile(userProfile: UserProfile)

    @Update
    fun updatePhotoProfile(userProfile: UserProfile)
}