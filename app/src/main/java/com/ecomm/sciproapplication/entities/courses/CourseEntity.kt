package com.ecomm.sciproapplication.entities.courses

import java.util.*

class CourseEntity(var uid: String, var pdfUrl: String, var saveDate: Date, var teacherId: String, var smalContent:String, var illustrationUrl:String) {
    constructor() : this(uid = "", pdfUrl = "", saveDate = Date(), teacherId = "", smalContent="", illustrationUrl = "")
}