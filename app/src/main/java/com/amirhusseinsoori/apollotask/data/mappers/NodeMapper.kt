package com.amirhusseinsoori.apollotask.data.mappers

import com.amirhusseinsoori.apollotask.common.Constance.EMPTY_STRING
import com.amirhusseinsoori.apollotask.data.db.entity.NodeEntity
import com.amirhusseinsoori.apollotask.data.db.entity.OwnerEntity
import com.amirhusseinsoori.apollotask.domain.model.NodeModel
import com.amirhusseinsoori.apollotask.domain.model.OwnerModel
import example.myapplication.GetListQuery


fun List<NodeEntity>.mapEntityListToModelList(): List<NodeModel> {
    return map { it.mapEntityToModel() }
}


fun NodeEntity.mapEntityToModel(): NodeModel {
    return NodeModel(
        id = id!!,
        name = name!!,
        owner = OwnerModel(
            avatarUrl = ownerEntity!!.avatarUrl,
            login = ownerEntity.login!!,
            url = ownerEntity.url!!
        )

    )
}

fun GetListQuery.Node.mapServerToEntity(): NodeEntity {
    return NodeEntity(
        name = name,
        ownerEntity = OwnerEntity(
            avatarUrl = if (owner.avatarUrl is String) owner.avatarUrl else EMPTY_STRING,
            login = owner.login,
            url = if (owner.url is String) owner.url else EMPTY_STRING
        )
    )
}


fun List<GetListQuery.Node?>.mapListServerToEntity(): List<NodeEntity> {
    return map { it!!.mapServerToEntity() }
}






