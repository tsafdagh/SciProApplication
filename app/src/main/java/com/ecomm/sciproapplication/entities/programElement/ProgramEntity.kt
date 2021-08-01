package com.ecomm.sciproapplication.entities.programElement

import java.util.*

data class ProgramEntity(
    var programId: String,
    var programTitle: String,
    var programContent: String,
    var saveAt: Date,
    var teacherId: String,
    var schoolId: String
) {
    var numberOfPart:Int = 0
    var startAt:Int = 0

    constructor() : this(
        programId = "",
        programTitle = "",
        programContent = "",
        saveAt = Date(),
        schoolId = "",
        teacherId = ""
    )
}
