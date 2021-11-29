package com.amirhusseinsoori.apollotask.data.mappers

import com.amirhusseinsoori.apollotask.common.Constance
import com.amirhusseinsoori.apollotask.data.db.entity.ProfileEntity
import com.amirhusseinsoori.apollotask.domain.model.ProfileModel
import example.myapplication.ProfileQuery

fun ProfileQuery.Data.mapToProfile(): ProfileEntity {
    return ProfileEntity(
        login = repositoryOwner!!.login,
        avatarUrl =if (repositoryOwner.avatarUrl is String) repositoryOwner.avatarUrl else Constance.EMPTY_STRING,
        url = if (repositoryOwner.url is String) repositoryOwner.url else Constance.EMPTY_STRING,
    )
}


fun ProfileEntity.mapToProfileModel(): ProfileModel {
    return ProfileModel(
        login = login,
        avatarUrl =if (avatarUrl is String) avatarUrl else Constance.EMPTY_STRING,
        url = if (url is String) url else Constance.EMPTY_STRING,
    )
}



