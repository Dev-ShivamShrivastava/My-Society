package com.domain.model.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateServiceRequest(
    var name: String?=null,
    var phoneNo: String?=null,
    var service: String?=null,
    var message: String?=null
)