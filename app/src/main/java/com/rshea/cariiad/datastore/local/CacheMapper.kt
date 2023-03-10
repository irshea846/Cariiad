package com.rshea.cariiad.datastore.local

import com.rshea.cariiad.models.University
import com.rshea.cariiad.utils.Converters
import com.rshea.cariiad.utils.EntityMapper

class CacheMapper :
    EntityMapper<UniversityCacheEntity, University> {

    override fun mapFromEntity(entity: UniversityCacheEntity): University {
        return University(
            name = entity.name,
            country = entity.country,
            webPagesList = Converters.toList(entity.webPages),
        )
    }

    override fun mapToEntity(id: Int, domainModel: University): UniversityCacheEntity {
        return UniversityCacheEntity(
            id = id,
            name = domainModel.name,
            country = domainModel.country,
            webPages = Converters.fromList(domainModel.webPagesList),
        )
    }

    override fun mapToEntity(domainModel: University): UniversityCacheEntity {
        TODO("Not yet implemented")
    }

    fun mapFromEntityList(entities: List<UniversityCacheEntity>): List<University>{
        return entities.map { mapFromEntity(it) }
    }

}