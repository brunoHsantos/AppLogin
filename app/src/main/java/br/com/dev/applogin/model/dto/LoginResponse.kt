package br.com.dev.applogin.model.dto

import java.io.Serializable

//Classe utilizada para fazer a consulta do usuário na api.
data class LoginResponse (
    val email: String?,
    val senha: String?
): Serializable
