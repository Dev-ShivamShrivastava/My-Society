package com.domain.model.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateUserRequest(
    var name: String? = null,
    var email: String? = null,
    var phoneNo: String? = null,
    var password: String? = null,
    var dob: String? = null
)
