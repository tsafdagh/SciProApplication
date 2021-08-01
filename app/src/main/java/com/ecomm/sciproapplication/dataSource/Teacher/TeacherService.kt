package com.ecomm.sciproapplication.dataSource.Teacher

import com.ecom.ecomm.utils.FirebaseResponseType
import com.ecomm.sciproapplication.entities.cheacher.Teacher
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TeacherService @Inject constructor(private val firebaseFirestore: FirebaseFirestore) {

    suspend fun saveTeacherAccountData(
        teacher: Teacher
    ): FirebaseResponseType<Boolean> {

        return try {
            firebaseFirestore
                .collection(TEACHER_COLLECTION)
                .document(teacher.uid)
                .set(teacher, SetOptions.merge())
                .await()
            FirebaseResponseType.FirebaseSuccessResponse(true)
        } catch (ex: Exception) {
            FirebaseResponseType.FirebaseErrorResponse(ex)
        }
    }

    suspend fun getTeacherById(
        teacherId: String
    ): FirebaseResponseType<Teacher> {

        return try {
           val snapShot = firebaseFirestore
                .collection(TEACHER_COLLECTION)
                .document(teacherId)
                .get()
                .await()
            val teacher = snapShot.toObject(Teacher::class.java)

            FirebaseResponseType.FirebaseSuccessResponse(teacher)
        } catch (ex: Exception) {
            FirebaseResponseType.FirebaseErrorResponse(ex)
        }
    }

    companion object {
        const val TEACHER_COLLECTION = "TEACHERS"
    }
}