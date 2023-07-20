package br.com.dev.applogin.model.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//Classe utilizada para armazenar os valores que recebemos da api, e tramamos como quiser.
//Nesse caso, populando os espaços desejados.
data class ProfileDetail (

    @SerializedName("_id") val _id: String,
    @SerializedName("nome") val nome: String,
    @SerializedName("idade") val idade: Int,
    @SerializedName("sexo") val sexo: String,
    @SerializedName("email") val email: String,
    @SerializedName("senha") val senha: String,

): Serializable
