package com.example.myapplication.data

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val main:CoroutineDispatcher
    val io:CoroutineDispatcher
    val default:CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}