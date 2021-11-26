package com.example.myapplication.data.mappers


import com.example.myapplication.data.db.enity.NodeEntity
import com.example.myapplication.data.db.enity.OwnerEntity
import com.example.myapplication.data.mappers.base.EntityMapper
import example.myapplication.GetListQuery
import javax.inject.Inject

class NodeMapper @Inject constructor() : EntityMapper<GetListQuery.Node, NodeEntity> {
    override fun mapFromEntity(entity: GetListQuery.Node): NodeEntity {
        return NodeEntity(
            name = entity.name,
            ownerEntity = OwnerEntity(
                avatarUrl = if(entity.owner.avatarUrl is String) entity.owner.avatarUrl else " ",
                login = entity.owner.login ,
                url = if(entity.owner.url is String) entity.owner.url else " "
            )
        )
    }

    override fun mapToEntity(domainModel: NodeEntity): GetListQuery.Node {
        return GetListQuery.Node(
            name = domainModel.name!!,
            owner = GetListQuery.Owner(
                avatarUrl = domainModel.name,
                login =domainModel.ownerEntity!!.login!!,
                url = domainModel.ownerEntity.url!!
            )

        )
    }

    override fun mapFromEntityList(entities: List<GetListQuery.Node>): List<NodeEntity> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(domains: List<NodeEntity>): List<GetListQuery.Node> {
        return domains.map { mapToEntity(it) } }



}