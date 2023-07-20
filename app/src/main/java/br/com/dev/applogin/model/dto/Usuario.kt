package br.com.dev.applogin.model.dto

import java.io.Serializable


//classe utilizada para criação do usuário com as respectivas informacoes solicitadas.
// Portanto, criamos a classe de acordo com esses valores declarados pela api. Ou seja, o que a api espera receber de valores.
data class Usuario (

    var nome: String?,
    var idade: Int?,
    var sexo: String?,
    val email: String?,
    val senha: String?

): Serializable