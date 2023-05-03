package br.com.dev.applogin.model.repository

import br.com.dev.applogin.model.dataClass.Profile

interface IRepositoryLocal {

    fun insertProfile(profile: Profile){}

    fun profileById(profileId: Int?): Profile?

    fun enterLogin(profile: Profile)

}
