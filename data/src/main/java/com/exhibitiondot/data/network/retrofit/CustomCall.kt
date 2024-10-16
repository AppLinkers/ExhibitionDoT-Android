package com.exhibitiondot.data.network.retrofit

import com.exhibitiondot.data.network.NetworkState
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class CustomCall<T : Any>(
    private val call: Call<T>
) : Call<NetworkState<T>> {
    override fun enqueue(callback: Callback<NetworkState<T>>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()?.string()
                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@CustomCall,
                            Response.success(NetworkState.Success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@CustomCall,
                            Response.success(
                                NetworkState.UnknownError(
                                    t = IllegalStateException("body is null"),
                                    errorState = "body is null"
                                )
                            )
                        )
                    }
                } else {
                    callback.onResponse(
                        this@CustomCall,
                        Response.success(NetworkState.Failure(code, error))
                    )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val errorResponse = when (t) {
                    is IOException -> NetworkState.NetworkError(t)
                    else -> NetworkState.UnknownError(t, "알 수 없는 에러")
                }
                callback.onResponse(
                    this@CustomCall,
                    Response.success(errorResponse)
                )
            }
        })
    }

    override fun clone(): Call<NetworkState<T>> = CustomCall(call.clone())

    override fun execute(): Response<NetworkState<T>> {
        throw UnsupportedOperationException("CustomAdapter를 사용합니다. execute를 호출하지 마세요.")
    }

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()
}
