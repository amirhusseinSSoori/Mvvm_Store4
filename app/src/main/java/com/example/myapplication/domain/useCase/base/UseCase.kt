package com.example.myapplication.domain.useCase.base

abstract class UseCase<in Params, Type>() where Type : Any {
    abstract suspend fun execute(params: Params? = null): Type
}