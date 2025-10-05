package com.data.api

import com.domain.model.request.CreateServiceRequest
import com.domain.model.request.CreateUserRequest
import com.domain.model.request.LoginRequest
import com.domain.model.response.CreateServiceResponse
import com.domain.model.response.CreateUserResponse
import com.domain.model.response.LoginResponse
import com.domain.model.response.StandardResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST(loginAPI)
    suspend fun loginApi(@Body loginRequest: LoginRequest): LoginResponse

    @POST(createUserAPI)
    suspend fun createUserApi(@Body createUserRequest: CreateUserRequest): CreateUserResponse

    @POST(createServiceRequestAPI)
    suspend fun createServiceRequestApi(@Body createServiceRequestRequest: CreateServiceRequest): CreateServiceResponse


}
