package com.example.myapplication.domain.useCase.allRepository

import com.example.myapplication.domain.exception.ApolloResult
import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.domain.repository.Repository
import com.example.myapplication.domain.useCase.base.UseCaseImmediate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowAllRepositoryUseCase @Inject constructor(
    private val appRepository: Repository
) {
     fun execute() = appRepository.getListRepFromSource()
}