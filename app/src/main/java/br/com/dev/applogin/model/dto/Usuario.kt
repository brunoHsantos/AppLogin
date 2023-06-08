package br.com.dev.applogin.model.dto

import java.io.Serializable


data class Usuario (

    var nome: String?,
    var idade: Int?,
    var sexo: String?,
    val email: String?,
    val senha: String?

): Serializable