package br.com.dev.applogin.localData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.dev.applogin.model.dto.Task
import br.com.dev.applogin.model.dto.UserProfile

@Database(entities = [Task::class, UserProfile::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private var INSTANCE: TaskDatabase? = null

        fun getDatabaseInstance(context: Context): TaskDatabase? {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    TaskDatabase::class.java,
                    "task-database"
                )
                .allowMainThreadQueries()
                .build()
            }

            return INSTANCE
        }
    }
}