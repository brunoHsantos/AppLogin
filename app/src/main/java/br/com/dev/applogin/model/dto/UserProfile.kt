package br.com.dev.applogin.model.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
class UserProfile(

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var profilePhoto: ByteArray? = null

) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}