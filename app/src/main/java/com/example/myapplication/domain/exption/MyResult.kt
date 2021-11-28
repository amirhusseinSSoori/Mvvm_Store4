package com.example.myapplication.domain.exption

import androidx.annotation.NonNull

data class MyResult<T>(val status: Int, val data: T?) {
    fun isError() = status == STATUS_ERROR
    fun isLoading() = status == STATUS_LOADING
    fun isSuccess() = status == STATUS_SUCCESS

    companion object {
        private const val STATUS_LOADING = 0
        private const val STATUS_SUCCESS = 1
        private const val STATUS_ERROR = -1

        /**
         * Helper method to create fresh state result
         */
        fun <T> success(@NonNull data: T): MyResult<T> {
            return MyResult(STATUS_SUCCESS, data)
        }

        /**
         * Helper method to create error state Result. Error state might also have the current data, if any
         */
        fun <T> error(item: T? = null): MyResult<T> {
            return MyResult(STATUS_ERROR, item)
        }

        /**
         * Helper method to create loading state Result.
         */
        fun <T> loading(data: T? = null): MyResult<T> {
            return MyResult(STATUS_LOADING, data)
        }
    }
}