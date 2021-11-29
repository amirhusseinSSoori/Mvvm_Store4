package com.amirhusseinsoori.apollotask.domain.interactor.account

import com.amirhusseinsoori.apollotask.domain.exption.SSOTResult
import com.amirhusseinsoori.apollotask.domain.interactor.base.UseCaseImmediate
import com.amirhusseinsoori.apollotask.domain.model.ProfileModel
import com.amirhusseinsoori.apollotask.domain.repository.ProfileRepositry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowAccountDetailsUseCase @Inject constructor(
    var repository: ProfileRepositry
) : UseCaseImmediate<Flow<SSOTResult<ProfileModel>>>() {
    override suspend fun buildUseCaseImmediate(): Flow<SSOTResult<ProfileModel>> =
        repository.getDetailsOfProfile()
}