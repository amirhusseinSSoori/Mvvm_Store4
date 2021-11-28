package com.example.myapplication.domain.interactor.account

import com.example.myapplication.domain.exption.SSOTResult
import com.example.myapplication.domain.interactor.base.UseCaseImmediate
import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.domain.model.ProfileModel
import com.example.myapplication.domain.repository.ProfileRepositry
import com.example.myapplication.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowAccountDetailsUseCase @Inject constructor(
    var repository: ProfileRepositry
) : UseCaseImmediate<Flow<SSOTResult<ProfileModel>>>() {
    override suspend fun buildUseCaseImmediate(): Flow<SSOTResult<ProfileModel>> =
        repository.getLatestProfile()
}