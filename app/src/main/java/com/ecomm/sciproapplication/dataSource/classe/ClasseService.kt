package com.ecomm.sciproapplication.dataSource.classe

import com.ecom.ecomm.utils.FirebaseResponseType
import com.ecomm.sciproapplication.dataSource.schools.SchoolService
import com.ecomm.sciproapplication.entities.classes.Classes
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ClasseService @Inject constructor(private val firebaseInstance: FirebaseFirestore) {

    suspend fun getAllClasses(schoolId: String): FirebaseResponseType<List<Classes>> {
        return try {
            val result = firebaseInstance.collection(SchoolService.SCHOOL_COLLECTION)
                .document(schoolId)
                .collection(CLASSES_COLLECTIONS)
                .get()
                .await()
            val data = result.toObjects(Classes::class.java)
            FirebaseResponseType.FirebaseSuccessResponse(data)
        } catch (ex: Exception) {
            FirebaseResponseType.FirebaseErrorResponse(ex)
        }
    }

    companion object {
        val CLASSES_COLLECTIONS = "CLASSES"
    }
}