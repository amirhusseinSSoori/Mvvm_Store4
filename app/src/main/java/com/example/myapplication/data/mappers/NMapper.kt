package com.example.myapplication.data.mappers

import com.example.myapplication.data.db.enity.NodeEntity
import com.example.myapplication.data.db.enity.OwnerEntity
import com.example.myapplication.data.mappers.base.EntityMapper
import example.myapplication.GetListQuery
import javax.inject.Inject

class NMapper  @Inject constructor(): EntityMapper<GetListQuery.Node, NodeEntity> {
    override  fun mapFromEntity(entity: GetListQuery.Node): NodeEntity {
        return NodeEntity(
            name = entity.name,
            ownerEntity = OwnerEntity(
                avatarUrl = entity.owner.avatarUrl.toString(),
                login = entity.owner.login,
                url = entity.owner.url.toString()
            )
        )
    }


    override fun mapToEntity(domainModel: NodeEntity): GetListQuery.Node {
        return GetListQuery.Node(
            name = domainModel.name!!,
            owner = GetListQuery.Owner(
                avatarUrl = domainModel.name,
                login =domainModel.ownerEntity!!.login!!,
                url = domainModel.ownerEntity!!.url!!
            )

        )
    }

    override fun mapFromEntityList(entities: List<GetListQuery.Node?>): List<NodeEntity> {
        return entities.map { mapFromEntity(it!!) }
    }

    override fun mapToEntityList(domains: List<NodeEntity>): List<GetListQuery.Node> {
        return domains.map { mapToEntity(it) }
    }


}
fun mapFromModelList(entities: List<GetListQuery.Node>): List<NodeModel> {
    return entities.map { it.mapTo() }
}
fun GetListQuery.Data.mapToDomainModel() = mapFromModelList(viewer!!.repositories.nodes as List<GetListQuery.Node>)


fun GetListQuery.Node.mapTo() = NodeModel(
    name = name,
    owner = OwnerModel(
        login = owner.login,
        avatarUrl = owner.avatarUrl.toString(),
        url = owner.url.toString()
    )
)
fun maptoEntityModel(entities: List<NodeEntity>): List<NodeModel> {
    return entities.map { mapToModel(it) }
}

fun mapToModel(domainModel: NodeEntity): NodeModel {
    return NodeModel(
        name = domainModel.name!!,
        owner = OwnerModel(
            avatarUrl = domainModel.name,
            login =domainModel.ownerEntity!!.login!!,
            url = domainModel.ownerEntity.url!!
        )

    )
}

