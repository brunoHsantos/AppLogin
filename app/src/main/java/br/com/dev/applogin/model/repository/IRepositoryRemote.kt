package br.com.dev.applogin.model.repository

import br.com.dev.applogin.model.dataClass.Profile

interface IRepositoryRemote {

    fun getProfileById(profileId: Int?, isSuccess: (Profile?) -> Unit, isError: () -> Unit)

    fun createProfile(profile: Profile, isSuccess: (Profile?) -> Unit, isError: () -> Unit)

}