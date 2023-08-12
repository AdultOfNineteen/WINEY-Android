package com.teamwiney.core.network.adapter

sealed class ApiResult<T> {

    class Success<T>(val data: T, val code: Int) : ApiResult<T>()

    class ApiError<T>(val message: String, val code: Int) : ApiResult<T>()

    class NetworkError<T>(val throwable: Throwable) : ApiResult<T>()

}