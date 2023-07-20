package br.com.dev.applogin.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tasks")
class Task(

    @PrimaryKey(autoGenerate = true)

    val id: Int = 0,
    val owner: String? = "",
    var title: String? = "",
    var description: String? = "",
    var dateLimit: Long,
    var isChecked: Boolean = false,


): Serializable