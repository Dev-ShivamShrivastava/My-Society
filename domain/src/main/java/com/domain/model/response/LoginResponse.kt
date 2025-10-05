package com.domain.model.response


import com.squareup.moshi.JsonClass

/**
{
    "status": "Success",
    "message": "Login successful",
    "data": {
        "id": "68e01ba17a59d4b2f67bc85b",
        "name": "shivam",
        "email": "shivam@gmail.com",
        "phoneNo": "6200968475",
        "dob": "27-11-2020",
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2OGUwMWJhMTdhNTlkNGIyZjY3YmM4NWIiLCJpYXQiOjE3NTk1NzYyMTksImV4cCI6MTc1OTU3OTgxOX0.mQdU61vs1c-57iaerCZHyrPiYK1t7ZneuxUDxI2aHx0"
    }
}
*/
@JsonClass(generateAdapter = true)
data class LoginResponse(
    var `data`: UserInfoData? = null,
    var message: String? = null,
    var status: String? = null
) {
    data class UserInfoData(
        var dob: String? = null,
        var email: String? = null,
        var id: String? = null,
        var name: String? = null,
        var phoneNo: String? = null,
        var token: String? = null
    )
}