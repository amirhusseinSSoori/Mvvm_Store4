package com.example.myapplication.data.mappers

import com.example.myapplication.data.db.enity.NodeEntity
import com.example.myapplication.data.db.enity.OwnerEntity
import example.myapplication.GetListQuery

//data class HelloModel(val info: InfoModel, val results: List<SingleCharacterModel>)

fun GetListQuery.Data.mapToDomainModel() = mapFromEntityList(repositoryOwner!!.repositories.nodes as List<GetListQuery.Node>)


fun GetListQuery.Node.mapTo() = NodeModel(
    name = name,
    owner = OwnerModel(
        login = owner.login,
        avatarUrl = owner.avatarUrl.toString(),
        url = owner.url.toString()
    )
)

fun mapFromEntityList(entities: List<GetListQuery.Node>): List<NodeModel> {
    return entities.map { it.mapTo() }
}

fun maptoEntityList(entities: List<NodeEntity>): List<NodeModel> {
    return entities.map { mapToEntity(it) }
}

 fun mapToEntity(domainModel: NodeEntity): NodeModel {
    return NodeModel(
        name = domainModel.name!!,
        owner = OwnerModel(
            avatarUrl = domainModel.name,
            login =domainModel.ownerEntity!!.login!!,
            url = domainModel.ownerEntity.url!!
        )

    )
}



















