package br.com.dev.applogin.model.repository

import br.com.dev.applogin.model.dataClass.Profile

interface IRepositoryLocal {

    fun insertPorfile(profile: Profile){}

    fun profileById(profileId: Int?): Profile?


}
