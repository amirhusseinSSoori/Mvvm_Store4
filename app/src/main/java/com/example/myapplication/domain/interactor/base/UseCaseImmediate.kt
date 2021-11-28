package com.example.myapplication.domain.interactor.base

abstract class UseCaseImmediate<out R> {
    protected abstract suspend fun buildUseCaseImmediate() : R
    suspend fun execute(): R = buildUseCaseImmediate()
}