package com.data.api

import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
    return try {
        val result = apiCall()
        Resource.Success(result)
    } catch (e: HttpException) {
        Resource.Error(
            message = when (e.code()) {
                400 -> "Bad Request"
                401 -> "Unauthorized"
                403 -> "Forbidden"
                404 -> "Not Found"
                500 -> "Internal Server Error"
                502 -> "Bad Gateway"
                else -> e.message ?: "Unexpected error"
            },
            code = e.code()
        )
    } catch (e: IOException) {
        Resource.Error("No internet connection")
    } catch (e: SocketTimeoutException) {
        Resource.Error("Request timed out")
    } catch (e: Exception) {
        Resource.Error("Unknown error: ${e.localizedMessage}")
    }
}