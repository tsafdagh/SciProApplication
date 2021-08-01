package com.ecomm.sciproapplication.entities.cheacher

import com.ecomm.sciproapplication.utils.UserAuthUtils
import java.io.Serializable
import java.util.*

data class Teacher(
    val uid: String = UserAuthUtils.getUserFirebaseUid(),
    var authPhoneNumber: String = UserAuthUtils.getUserPhone(),
    var matricule:String,
    var name: String,
    var firstName: String,
    var email: String,
    var createdAt: Date,
    var schoolsIds: List<String> ,
    var classesIds: List<String>,
    var matiereIds: List<String>
): Serializable {
    constructor() : this(
        uid =UserAuthUtils.getUserFirebaseUid(),
        authPhoneNumber = UserAuthUtils.getUserPhone(),
        matricule ="",
        name = "",
        firstName = "",
        email = "",
        createdAt = Date(),
        schoolsIds = arrayListOf(),
        classesIds = arrayListOf(),
        matiereIds = arrayListOf()
    )
}