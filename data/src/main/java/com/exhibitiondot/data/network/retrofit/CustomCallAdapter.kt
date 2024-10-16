package com.exhibitiondot.data.network.retrofit

import com.exhibitiondot.data.network.NetworkState
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CustomCallAdapter<R : Any>(
    private val responseType: Type
) : CallAdapter<R, Call<NetworkState<R>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): Call<NetworkState<R>> = CustomCall(call)

    class Factory : CallAdapter.Factory() {
        override fun get(
            returnType: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ): CallAdapter<*, *>? {
            if (getRawType(returnType) != Call::class.java) {
                return null
            }

            val responseType = getParameterUpperBound(0, returnType as ParameterizedType)
            if (getRawType(responseType) != NetworkState::class.java) {
                return null
            }

            val bodyType = getParameterUpperBound(0, responseType as ParameterizedType)
            return CustomCallAdapter<Any>(bodyType)
        }
    }
}