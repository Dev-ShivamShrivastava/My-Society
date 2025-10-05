package com.domain.model.response

/**
{
    "status": "Success",
    "message": "Pending requests fetched successfully",
    "data": [
        {
            "_id": "68e218c14f742b20fd45fa83",
            "userId": "68e01ba17a59d4b2f67bc85b",
            "name": "chhg",
            "phoneNo": "9665965555",
            "service": "House Cleaning",
            "message": "TV bvbbj",
            "status": "Pending",
            "createdAt": "2025-10-05T07:05:37.351Z",
            "__v": 0
        }
    ]
}
*/
data class ServiceTicketListResponse(
    var `data`: List<ServiceTicketData>? = null,
    var message: String? = null,
    var status: String? = null
) {
    data class ServiceTicketData(
        var __v: Int? = null,
        var _id: String? = null,
        var createdAt: String? = null,
        var message: String? = null,
        var name: String? = null,
        var phoneNo: String? = null,
        var service: String? = null,
        var status: String? = null,
        var userId: String? = null
    )
}