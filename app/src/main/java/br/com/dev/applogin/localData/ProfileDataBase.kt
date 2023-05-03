package br.com.dev.applogin.localData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.dev.applogin.model.dataClass.Profile


@Database(entities = [Profile::class], version = 1)

abstract class ProfileDataBase: RoomDatabase() {

    abstract fun profileDao(): LoginDao

    companion object {
        private var INSTANCE: ProfileDataBase? = null
        fun getDatabaseInstance(context: Context): ProfileDataBase? {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    ProfileDataBase::class.java,
                    "profile-database"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE
        }
    }
}