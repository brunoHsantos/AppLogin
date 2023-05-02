package br.com.dev.applogin.model.dataClass

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "profile")

data class Profile (

    @PrimaryKey(autoGenerate = true)

    val id: Int = 0,

    var age: Int? = null,
    var name: String? = "",
    var sex: String? = "",
    val email: String? = "",
    val password: String? = ""

): Serializable