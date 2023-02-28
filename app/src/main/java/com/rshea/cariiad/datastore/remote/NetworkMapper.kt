package com.rshea.cariiad.datastore.remote

import com.rshea.cariiad.models.University
import com.rshea.cariiad.utils.EntityMapper

class NetworkMapper :
    EntityMapper<UniversityNetworkEntity, University> {

        override fun mapFromEntity(entity: UniversityNetworkEntity): University {
            return University(
                name = entity.name,
                country = entity.country,
                webPagesList = entity.webPagesListHolder,
            )
        }

        override fun mapToEntity(domainModel: University): UniversityNetworkEntity {
            return UniversityNetworkEntity(
                name = domainModel.name,
                country = domainModel.country,
                webPagesListHolder = domainModel.webPagesList,
            )
        }

        fun mapFromEntityList(entities: List<UniversityNetworkEntity>): List<University>{
            return entities.map { mapFromEntity(it) }
        }
}