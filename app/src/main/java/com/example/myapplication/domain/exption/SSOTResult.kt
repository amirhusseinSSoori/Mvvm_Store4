package com.example.myapplication.domain.exption

import androidx.annotation.NonNull

data class SSOTResult<T>(val status: Int, val data: T?,val msg: String?=null) {
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
        fun <T> success(@NonNull data: T): SSOTResult<T> {
            return SSOTResult(STATUS_SUCCESS, data)
        }

        /**
         * Helper method to create error state Result. Error state might also have the current data, if any
         */
        fun <T> error(item: T? = null,msg:String?=null): SSOTResult<T> {
            return SSOTResult(STATUS_ERROR, null,msg)
        }

        /**
         * Helper method to create loading state Result.
         */
        fun <T> loading(data: T? = null): SSOTResult<T> {
            return SSOTResult(STATUS_LOADING, data)
        }
    }
}