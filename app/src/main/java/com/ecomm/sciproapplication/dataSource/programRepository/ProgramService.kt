package com.ecomm.sciproapplication.dataSource.programRepository

import com.ecom.ecomm.utils.FirebaseResponseType
import com.ecomm.sciproapplication.dataSource.classe.ClasseService
import com.ecomm.sciproapplication.dataSource.schools.SchoolService
import com.ecomm.sciproapplication.entities.programElement.ProgramEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProgramService @Inject constructor(private val firebaseInstance: FirebaseFirestore) {

    suspend fun saveProgram(
        programEntity: ProgramEntity,
        classeId: String
    ): FirebaseResponseType<Boolean> {

        return try {
            firebaseInstance.collection(SchoolService.SCHOOL_COLLECTION)
                .document(programEntity.schoolId)
                .collection(ClasseService.CLASSES_COLLECTIONS)
                .document(classeId)
                .collection(PROGRAM_COLLECTION)
                .document(programEntity.programId)
                .set(programEntity, SetOptions.merge())
                .await()

            FirebaseResponseType.FirebaseSuccessResponse(true)
        } catch (ex: Exception) {
            FirebaseResponseType.FirebaseErrorResponse(ex)
        }
    }

    suspend fun getAllProgramOfSchool(
        schoolId: String,
        classeId: String
    ): FirebaseResponseType<List<ProgramEntity>> {
        return try {
            val result = firebaseInstance.collection(SchoolService.SCHOOL_COLLECTION)
                .document(schoolId)
                .collection(ClasseService.CLASSES_COLLECTIONS)
                .document(classeId)
                .collection(PROGRAM_COLLECTION)
                .get()
                .await()

            val data = result.toObjects(ProgramEntity::class.java)
            FirebaseResponseType.FirebaseSuccessResponse(data)
        } catch (ex: Exception) {
            FirebaseResponseType.FirebaseErrorResponse(ex)
        }
    }

    companion object {
        private val PROGRAM_COLLECTION = "PROGRAM_COLLECTION"
    }
}