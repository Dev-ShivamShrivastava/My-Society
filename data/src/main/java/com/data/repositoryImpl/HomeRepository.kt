package com.data.repositoryImpl

import com.data.api.ApiInterface
import com.data.api.safeApiCall
import com.domain.api.AppResult
import com.domain.model.request.CreateServiceRequest
import com.domain.model.request.CreateUserRequest
import com.domain.model.request.LoginRequest
import com.domain.model.response.CreateServiceResponse
import com.domain.model.response.CreateUserResponse
import com.domain.model.response.LoginResponse
import com.domain.model.response.ServiceListResponse
import com.domain.model.response.StandardResponse
import com.domain.repository.IAuthRepository
import com.domain.repository.IHomeRepository
import javax.inject.Inject


class HomeRepository @Inject constructor(val apiInterface: ApiInterface) : IHomeRepository {

    override suspend fun createServiceRequestApi(createServiceRequestRequest: CreateServiceRequest): AppResult<CreateServiceResponse> {
        return safeApiCall{ apiInterface.createServiceRequestApi(createServiceRequestRequest) }
    }

    override suspend fun getServiceListApi(): AppResult<ServiceListResponse> {
        return safeApiCall{ apiInterface.getServiceListApi() }
    }
}