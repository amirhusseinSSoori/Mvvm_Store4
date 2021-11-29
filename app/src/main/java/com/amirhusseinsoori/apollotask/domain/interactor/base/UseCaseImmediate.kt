package com.amirhusseinsoori.apollotask.domain.interactor.base

abstract class UseCaseImmediate<out R> {
    protected abstract suspend fun buildUseCaseImmediate() : R
    suspend fun execute(): R = buildUseCaseImmediate()
}