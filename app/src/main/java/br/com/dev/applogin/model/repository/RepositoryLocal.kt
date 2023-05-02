package br.com.dev.applogin.model.repository

import br.com.dev.applogin.localData.ProfileDataBase
import br.com.dev.applogin.model.dataClass.Profile

class RepositoryLocal(val profileLocal: ProfileDataBase? = null): IRepositoryLocal {


    override fun insertPorfile(profile: Profile) {
        profileLocal?.profileDao()?.insert(profile)
    }


    override fun profileById(profileId: Int?): Profile? {
       return profileLocal?.profileDao()?.getProfileById(profileId)
    }
}