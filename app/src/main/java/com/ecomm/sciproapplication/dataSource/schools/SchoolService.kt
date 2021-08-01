package com.ecomm.sciproapplication.dataSource.schools

import com.ecom.ecomm.utils.FirebaseResponseType
import com.ecomm.sciproapplication.entities.school.School
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SchoolService @Inject constructor(private val firebaseInstance: FirebaseFirestore) {

    suspend fun getAllSchools(): FirebaseResponseType<List<School>> {
        return try {
            val result = firebaseInstance.collection(SCHOOL_COLLECTION)
                .get()
                .await()
            val data = result.toObjects(School::class.java)
            FirebaseResponseType.FirebaseSuccessResponse(data)
        } catch (ex: Exception) {
            FirebaseResponseType.FirebaseErrorResponse(ex)
        }
    }

    suspend fun getALLSchoolWhereCheacherIn(cheacherId: String): FirebaseResponseType<List<School>> {
        return try {
            val result = firebaseInstance.collection(SCHOOL_COLLECTION)
                .whereArrayContains(School::staffIds.name, cheacherId)
                .get()
                .await()
            val data = result.toObjects(School::class.java)
            FirebaseResponseType.FirebaseSuccessResponse(data)
        } catch (ex: Exception) {
            FirebaseResponseType.FirebaseErrorResponse(ex)
        }
    }

    companion object {
        const val SCHOOL_COLLECTION = "SCHOOLS"
    }
}