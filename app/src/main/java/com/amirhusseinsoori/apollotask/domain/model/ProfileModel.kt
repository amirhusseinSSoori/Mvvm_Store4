package com.amirhusseinsoori.apollotask.domain.model

data class ProfileModel(
    var id: Int? = null,
    var login: String? = null,
    var avatarUrl: String? = null,
    var url: String? = null,
)