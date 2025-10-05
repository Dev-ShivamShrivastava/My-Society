package com.domain.repository

import com.domain.model.request.LoginRequest
import com.domain.model.response.StandardResponse
import com.domain.api.AppResult
import com.domain.model.request.CreateServiceRequest
import com.domain.model.request.CreateUserRequest
import com.domain.model.response.CreateServiceResponse
import com.domain.model.response.CreateUserResponse
import com.domain.model.response.LoginResponse
import com.domain.model.response.ServiceListResponse

interface IHomeRepository {
    suspend fun createServiceRequestApi(createServiceRequestRequest: CreateServiceRequest): AppResult<CreateServiceResponse>
    suspend fun getServiceListApi(): AppResult<ServiceListResponse>
}