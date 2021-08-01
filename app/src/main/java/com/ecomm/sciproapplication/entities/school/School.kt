package com.ecomm.sciproapplication.entities.school

import java.io.Serializable

class School(
    var schoolId: String,
    var name: String,
    var city: String,
    var contactEmail: String,
    var contactPhone: String,
    var country: String,
    var logoUrl: String,
    var slogan: String,
    var staffIds: ArrayList<String>
) : Serializable {
    constructor() : this(
        schoolId = "",
        name = "",
        city = "",
        contactEmail = "",
        contactPhone = "",
        country = "",
        logoUrl = "",
        slogan = "",
        staffIds = arrayListOf()
    )
}