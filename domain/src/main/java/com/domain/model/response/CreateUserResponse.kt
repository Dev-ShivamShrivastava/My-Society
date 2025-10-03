package com.domain.model.response


/**
{
    "status": "Success",
    "message": "User created successfully",
    "data": {
        "id": "68e01dc00d9c20f1adb52de0",
        "name": "shivam",
        "email": "shivam1@gmail.com",
        "phoneNo": "6200968475",
        "dob": "27-11-2020"
    }
}
*/
data class CreateUserResponse(
    var `data`: UserData? = null,
    var message: String? = null,
    var status: String? = null
) {
    data class UserData(
        var dob: String? = null,
        var email: String? = null,
        var id: String? = null,
        var name: String? = null,
        var phoneNo: String? = null
    )
}