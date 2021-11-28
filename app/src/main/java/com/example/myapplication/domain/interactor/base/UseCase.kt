package com.example.myapplication.domain.interactor.base




abstract class UseCase<out R> {
    protected abstract suspend fun buildUseCase() : R
    suspend fun execute(): R = buildUseCase()
}