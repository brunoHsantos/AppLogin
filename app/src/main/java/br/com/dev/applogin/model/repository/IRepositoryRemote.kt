package br.com.dev.applogin.model.repository

import br.com.dev.applogin.model.dto.IdProfile
import br.com.dev.applogin.model.dto.LoginResponse
import br.com.dev.applogin.model.dto.ProfileDetail
import br.com.dev.applogin.model.dto.Usuario

interface IRepositoryRemote {

    fun getProfileById(_id: String, isLoading: (Boolean) -> Unit, isSuccess: (ProfileDetail?) -> Unit, isError: () -> Unit)

    fun createProfile(profile: Usuario, isSuccess: () -> Unit, isError: () -> Unit)

    fun enterLogin(login: LoginResponse, isSuccess: (IdProfile?) -> Unit, isError: () -> Unit)

}