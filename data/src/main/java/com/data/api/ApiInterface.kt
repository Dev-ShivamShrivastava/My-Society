package com.data.api

import com.domain.model.request.CreateUserRequest
import com.domain.model.request.LoginRequest
import com.domain.model.response.CreateUserResponse
import com.domain.model.response.StandardResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST(loginAPI)
    suspend fun loginApi(@Body loginRequest: LoginRequest): StandardResponse

    @POST(createUserAPI)
    suspend fun createUserApi(@Body createUserRequest: CreateUserRequest): CreateUserResponse


}
