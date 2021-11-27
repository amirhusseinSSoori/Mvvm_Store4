package com.example.myapplication.domain.useCase.base

abstract class UseCaseImmediate< R> {
    protected abstract fun buildUseCaseImmediate() : R
    fun execute(): R = buildUseCaseImmediate()
}