package com.ecomm.sciproapplication.entities.classes

import java.io.Serializable

data class Classes(var abreviation: String, var name: String): Serializable {
    constructor() : this(abreviation = "", name = "")
}
