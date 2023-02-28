package com.rshea.cariiad.repository

import com.rshea.cariiad.datastore.local.CacheMapper
import com.rshea.cariiad.datastore.local.UniversityDao
import com.rshea.cariiad.datastore.remote.NetworkMapper
import com.rshea.cariiad.datastore.remote.UniversityRetrofit
import com.rshea.cariiad.models.University
import com.rshea.cariiad.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class UniversityRepository
    constructor(
        private val universityDao: UniversityDao,
        private val universityRetrofit: UniversityRetrofit,
        private val cacheMapper: CacheMapper,
        private val networkMapper: NetworkMapper
    ) {

        companion object {
            const val baseUrl = "http://universities.hipolabs.com/"
        }

        suspend fun fetchUniInfoList(): Flow<DataState<List<University>>> = flow {
            emit(DataState.Loading)
            try {
                val networkUniversities = universityRetrofit.get()
                var universities = networkMapper.mapFromEntityList(networkUniversities)
                for (university in universities) {
                    universityDao.insert(cacheMapper.mapToEntity(university))
                }
                val cachedUniversities = universityDao.get()
                universities = cacheMapper.mapFromEntityList(cachedUniversities)
                emit(DataState.Success(universities))
            } catch (e: Exception) {
                val cachedUniversities = universityDao.get()
                if (cachedUniversities.isNotEmpty()) {
                    emit(DataState.Success(cacheMapper.mapFromEntityList(cachedUniversities)))
                } else {
                    emit(DataState.Error(e))
                }
            }
        }
}