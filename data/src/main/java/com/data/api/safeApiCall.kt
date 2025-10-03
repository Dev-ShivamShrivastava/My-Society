package com.data.api

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import com.domain.api.AppResult
import com.domain.model.response.StandardResponse
import com.google.gson.Gson


suspend fun <T> safeApiCall(apiCall: suspend () -> T): AppResult<T> {
    return try {
        val result = apiCall()
        AppResult.Success(result)
    } catch (e: HttpException) {
        AppResult.Error(
            message = when (e.code()) {
                400 -> {
                    val error = e.response()?.errorBody()?.string()
                    println(error)

                    val errorBody = Gson().fromJson(error, StandardResponse::class.java)
                    println(errorBody)
                    errorBody.message?:"Bad Request"
                }
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
        AppResult.Error("No internet connection")
    } catch (e: SocketTimeoutException) {
        AppResult.Error("Request timed out")
    } catch (e: Exception) {
        AppResult.Error("Unknown error: ${e.localizedMessage}")
    }
}