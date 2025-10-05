package com.domain.model.response

/**
{
    "status": "Success",
    "message": "Service list fetched successfully",
    "data": {
        "whatsappNo": "919876543210",
        "mobileNo": "9123456789",
        "services": [
            "AC Repair",
            "Plumbing",
            "Electrician",
            "House Cleaning",
            "Painting",
            "Carpentry"
        ]
    }
}
*/
data class ServiceListResponse(
    var `data`: ServiceListData? = null,
    var message: String? = null,
    var status: String? = null
) {
    data class ServiceListData(
        var mobileNo: String? = null,
        var services: List<String>? = null,
        var whatsappNo: String? = null
    )
}