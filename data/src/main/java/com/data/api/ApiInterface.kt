package com.data.api

import com.domain.model.request.CreateServiceRequest
import com.domain.model.request.CreateUserRequest
import com.domain.model.request.LoginRequest
import com.domain.model.response.CreateServiceResponse
import com.domain.model.response.CreateUserResponse
import com.domain.model.response.LoginResponse
import com.domain.model.response.ServiceListResponse
import com.domain.model.response.ServiceTicketListResponse
import com.domain.model.response.StandardResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @POST(loginAPI)
    suspend fun loginApi(@Body loginRequest: LoginRequest): LoginResponse

    @POST(createUserAPI)
    suspend fun createUserApi(@Body createUserRequest: CreateUserRequest): CreateUserResponse

    @POST(createServiceRequestAPI)
    suspend fun createServiceRequestApi(@Body createServiceRequestRequest: CreateServiceRequest): CreateServiceResponse

    @GET(getServiceListAPI)
    suspend fun getServiceListApi(): ServiceListResponse

    @GET(getRequestsByStatusAPI)
    suspend fun getServiceTicketListApi(@Query("status") status: String): ServiceTicketListResponse


}
