package com.example.challenge.network.util

import retrofit2.Response

object NetworkRequestHandler{
    suspend fun <T> executeRequest(call: suspend () -> Response<T>): NetworkResponse<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                NetworkResponse.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string() ?: response.message()
                NetworkResponse.Error(Exception(errorBody))
            }
        } catch (e: Exception) {
            NetworkResponse.Failure(e)
        }
    }
}


sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val exception: Throwable) : NetworkResponse<Nothing>()
    data class Failure(val exception: Throwable) : NetworkResponse<Nothing>()
}



