package br.com.dev.applogin.model.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProfileDetail (

    @SerializedName("_id") val _id: String,
    @SerializedName("nome") val nome: String,
    @SerializedName("idade") val idade: Int,
    @SerializedName("sexo") val sexo: String,
    @SerializedName("eamil") val email: String,
    @SerializedName("senha") val senha: String,


): Serializable
