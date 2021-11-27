package com.example.myapplication.domain.exception

sealed class ApolloResult<out T> {
    class Empty<T> : ApolloResult<T>()
    data class Success<out T>(val data: T) : ApolloResult<T>()
    data class Error(val exception: Throwable) : ApolloResult<Nothing>()
    object Loading : ApolloResult<Nothing>()
}

inline fun <T : Any> ApolloResult<T>.onSuccess(action: (T) -> Unit): ApolloResult<T> {
    if (this is ApolloResult.Success) action(data)
    return this
}

inline fun <T : Any> ApolloResult<T>.onError(action: (Throwable) -> Unit): ApolloResult<T> {
    if (this is ApolloResult.Error) action(exception)
    return this
}

inline fun <T : Any> ApolloResult<T>.onLoading(action: () -> Unit): ApolloResult<T> {
    if (this is ApolloResult.Loading) action()
    return this
}