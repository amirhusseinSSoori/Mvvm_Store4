package com.example.myapplication.domain.interactor.repository

import com.example.myapplication.domain.interactor.base.UseCaseImmediate
import com.example.myapplication.domain.exption.SSOTResult
import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowAllRepositoryUseCase @Inject constructor(
    var repository: Repository
) : UseCaseImmediate<Flow<SSOTResult<List<NodeModel>>>>() {
    override suspend fun buildUseCaseImmediate(): Flow<SSOTResult<List<NodeModel>>> =
        repository.getLatestRepositories()
}