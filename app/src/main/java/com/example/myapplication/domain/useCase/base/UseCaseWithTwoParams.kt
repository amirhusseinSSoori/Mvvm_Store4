package com.example.myapplication.domain.useCase.base

abstract class UseCaseWithTwoParams<in ParamsOne, in ParamsTwo, out R> {
    protected abstract suspend fun buildUseCase(params: ParamsOne, params2: ParamsTwo): R
    suspend fun execute(params: ParamsOne, params2: ParamsTwo): R = buildUseCase(params,params2)
}