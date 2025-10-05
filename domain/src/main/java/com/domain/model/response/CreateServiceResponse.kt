package com.domain.model.response

/**
{
    "status": "Success",
    "message": "Service request created successfully",
    "data": {
        "userId": "68e01ba17a59d4b2f67bc85b",
        "name": "shivam",
        "phoneNo": "6200968475",
        "service": "Glass Brake",
        "message": "Please fix it ASAP",
        "_id": "68e17247e24d93f977073e90",
        "createdAt": "2025-10-04T19:15:19.739Z",
        "__v": 0
    }
}
*/
data class CreateServiceResponse(
    var `data`: CreateServiceData? = null,
    var message: String? = null,
    var status: String? = null
) {
    data class CreateServiceData(
        var __v: Int? = null,
        var _id: String? = null,
        var createdAt: String? = null,
        var message: String? = null,
        var name: String? = null,
        var phoneNo: String? = null,
        var service: String? = null,
        var userId: String? = null
    )
}