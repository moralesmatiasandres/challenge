package com.example.challenge.network.util

import retrofit2.Response

sealed class NetworkRequestHandler<out T> {
    data class Success<out T>(val data: T) : NetworkRequestHandler<T>()
    data class Error(val exception: Throwable) : NetworkRequestHandler<Nothing>()
    data class Failure(val exception: Throwable) : NetworkRequestHandler<Nothing>()
}

suspend fun <T> executeRequest(call: suspend () -> Response<T>): NetworkRequestHandler<T> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            NetworkRequestHandler.Success(response.body()!!)
        } else {
            NetworkRequestHandler.Error(Exception(response.message()))
        }
    } catch (e: Exception) {
        NetworkRequestHandler.Failure(e)
    }
}