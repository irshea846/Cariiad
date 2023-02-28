package com.rshea.cariiad.di

import com.rshea.cariiad.datastore.local.CacheMapper
import com.rshea.cariiad.datastore.local.UniversityDao
import com.rshea.cariiad.datastore.remote.NetworkMapper
import com.rshea.cariiad.datastore.remote.UniversityRetrofit
import com.rshea.cariiad.repository.UniversityRepository

object RepositoryModule {

    fun provideUniversityRepository(
        universityDao: UniversityDao,
        universityRetrofit: UniversityRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): UniversityRepository {
        return UniversityRepository(universityDao, universityRetrofit, cacheMapper, networkMapper)
    }
}