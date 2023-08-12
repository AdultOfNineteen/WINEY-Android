package com.teamwiney.core.network.adapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResultCallAdapterFactory : CallAdapter.Factory() {

    @Suppress("UNCHECKED_CAST")
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) return null
        check(returnType is ParameterizedType)

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != ApiResult::class.java) return null
        check (responseType is ParameterizedType)

        val successType = getParameterUpperBound(0, responseType)

        return object : CallAdapter<Any, Call<ApiResult<*>>> {
            override fun responseType(): Type =
                successType

            override fun adapt(call: Call<Any>): Call<ApiResult<*>> =
                ApiResultCall(call) as Call<ApiResult<*>>
        }
    }

}