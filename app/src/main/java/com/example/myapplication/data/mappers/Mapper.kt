package com.example.myapplication.data.mappers

import com.example.myapplication.common.Constance.EMPTY_STRING
import com.example.myapplication.data.db.entity.NodeEntity
import com.example.myapplication.data.db.entity.OwnerEntity
import com.example.myapplication.data.db.entity.ProfileEntity
import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.domain.model.OwnerModel
import com.example.myapplication.domain.model.ProfileModel
import example.myapplication.GetListQuery
import example.myapplication.ProfileQuery


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

fun ProfileQuery.Data.mapToProfile():ProfileEntity{

    return ProfileEntity(
        login = repositoryOwner!!.login,
        avatarUrl =if (repositoryOwner.avatarUrl is String) repositoryOwner.avatarUrl else EMPTY_STRING,
        url = if (repositoryOwner.url is String) repositoryOwner.url else EMPTY_STRING,
    )


}




