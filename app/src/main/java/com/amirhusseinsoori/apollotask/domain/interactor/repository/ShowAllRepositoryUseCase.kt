package com.amirhusseinsoori.apollotask.domain.interactor.repository

import com.amirhusseinsoori.apollotask.domain.interactor.base.UseCaseImmediate
import com.amirhusseinsoori.apollotask.domain.exption.SSOTResult
import com.amirhusseinsoori.apollotask.domain.model.NodeModel
import com.amirhusseinsoori.apollotask.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowAllRepositoryUseCase @Inject constructor(
    var repository: Repository
) : UseCaseImmediate<Flow<SSOTResult<List<NodeModel>>>>() {
    override suspend fun buildUseCaseImmediate(): Flow<SSOTResult<List<NodeModel>>> =
        repository.getLatestRepositories()
}