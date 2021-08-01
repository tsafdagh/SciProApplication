package com.ecomm.sciproapplication.entities.student

import com.ecomm.sciproapplication.utils.UserAuthUtils
import java.io.Serializable
import java.util.*

data class Student(
    val uid: String = UserAuthUtils.getUserFirebaseUid(),
    var authPhoneNumber: String = UserAuthUtils.getUserPhone(),
    var name: String,
    var firstName: String,
    var email: String,
    var matricule: String,
    var createdAt: Date,
    var classeLevel: String,
    var currentSchoolId: String,
    var schoolIds: MutableList<String>
) :Serializable{
    constructor() : this(
        uid = UserAuthUtils.getUserFirebaseUid(),
        authPhoneNumber = UserAuthUtils.getUserPhone(),
        name = "",
        firstName = "",
        email = "",
        matricule = "",
        createdAt = Date(),
        classeLevel = "",
        currentSchoolId = "",
        schoolIds = mutableListOf()
    )
}
