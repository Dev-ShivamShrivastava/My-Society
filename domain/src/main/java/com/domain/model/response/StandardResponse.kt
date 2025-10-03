package com.domain.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StandardResponse(
    var status: String? = null,
    var message: String? = null,
)
