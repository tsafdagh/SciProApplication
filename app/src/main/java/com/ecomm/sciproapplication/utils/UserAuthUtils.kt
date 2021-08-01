package com.ecomm.sciproapplication.utils

import com.google.firebase.auth.FirebaseAuth

object UserAuthUtils {

    fun getUserPhone():String{
        return FirebaseAuth.getInstance().currentUser?.phoneNumber?:throw (Throwable("Auth user is null"))
    }

    fun getUserFirebaseUid():String{
        return FirebaseAuth.getInstance().currentUser?.uid?:throw (Throwable("Auth user is null"))
    }
}