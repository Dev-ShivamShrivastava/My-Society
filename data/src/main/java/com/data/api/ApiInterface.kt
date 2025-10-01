package com.data.api

import com.domain.model.request.LoginRequest
import com.domain.model.response.StandardResponse

interface ApiInterface {

//    @Post(loginApi)
    suspend fun loginApi(loginRequest: LoginRequest): StandardResponse
}
