package com.example.myapplication.domain.useCase.allRepository


import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.domain.repository.Repository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowAllRepositoryUseCase @Inject constructor(
    private val appRepository: Repository
) {
     suspend fun execute() = appRepository.getLatestNews()
}