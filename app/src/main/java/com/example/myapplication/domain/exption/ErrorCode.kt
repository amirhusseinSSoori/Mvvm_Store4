package com.example.myapplication.domain.exption

import retrofit2.HttpException

private const val BAD_REQUEST_ERROR_MESSAGE = "Bad Request!"
private const val FORBIDDEN_ERROR_MESSAGE = "Forbidden!"
private const val NOT_FOUND_ERROR_MESSAGE = "Not Found!"
private const val METHOD_NOT_ALLOWED_ERROR_MESSAGE = "Method Not Allowed!"
private const val CONFLICT_ERROR_MESSAGE = "Conflict!"
private const val UNAUTHORIZED_ERROR_MESSAGE = "Unauthorized!"
private const val INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server error!"
private const val NO_CONNECTION_ERROR_MESSAGE = "No Connection!"
private const val TIMEOUT_ERROR_MESSAGE = "Time Out!"
const val UNKNOWN_ERROR_MESSAGE = "Unknown Error!"




fun Throwable.errorCode(): String {
    return if (this is HttpException) {
        when (this.code()) {
            404 -> BAD_REQUEST_ERROR_MESSAGE
            403 -> FORBIDDEN_ERROR_MESSAGE
            401 -> UNAUTHORIZED_ERROR_MESSAGE
            404 -> NOT_FOUND_ERROR_MESSAGE
            405 -> METHOD_NOT_ALLOWED_ERROR_MESSAGE
            409 -> CONFLICT_ERROR_MESSAGE
            500 -> INTERNAL_SERVER_ERROR_MESSAGE
            else -> NO_CONNECTION_ERROR_MESSAGE
        }
    } else {
        NO_CONNECTION_ERROR_MESSAGE
    }
}