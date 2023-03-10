package com.rshea.cariiad.utils

interface EntityMapper <Entity, DomainModel>{

    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity

    fun mapToEntity(id: Int, domainModel: DomainModel): Entity
}