package br.com.dev.applogin.localData



import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.dev.applogin.model.dataClass.Profile



@Dao
interface LoginDao {

    @Insert
    fun insert(profile: Profile)


    @Query("SELECT * FROM profile WHERE id = :profileId")
    fun getProfileById(profileId: Int?): Profile

}