package br.com.dev.applogin.model.dto

import java.io.Serializable

data class LoginResponse (
    val email: String?,
    val senha: String?
): Serializable
