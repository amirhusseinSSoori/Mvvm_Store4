package com.example.myapplication.domain.useCase.base

abstract class UseCaseWithParamsImmediate<in Params, out R> {
    protected abstract fun buildUseCaseImmediate(params: Params) : R
    fun execute(params: Params): R = buildUseCaseImmediate(params)
}