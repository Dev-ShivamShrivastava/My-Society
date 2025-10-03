package com.domain.model.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(var email: String?=null, var password: String?=null)
