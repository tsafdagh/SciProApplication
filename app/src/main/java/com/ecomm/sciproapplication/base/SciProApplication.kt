package com.ecomm.sciproapplication.base

import android.app.Application
import com.ecomm.sciproapplication.dataSource.Teacher.TeacherService
import com.ecomm.sciproapplication.dataSource.student.StudentService
import com.ecomm.sciproapplication.entities.cheacher.Teacher
import com.ecomm.sciproapplication.entities.student.Student
import com.ecomm.sciproapplication.utils.UserAuthUtils
import com.ecomm.sciproapplication.utils.getDataFlow
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@HiltAndroidApp
class SciProApplication : Application() {

    companion object {
        lateinit var currentTeacher: Teacher
        lateinit var currentStudent: Student

        fun loadCurrentTeacher() {
            getCurrentTeacherFromFirebase().onEach { teacher ->
                teacher?.let {
                    currentTeacher = it
                }
            }
        }

        fun loadCurrentStudent() {
            getCurrentStudentFromFirebase().onEach { student ->
                student?.let {
                    currentStudent = it
                }
            }
        }

        private fun getCurrentTeacherFromFirebase(): Flow<Teacher?> {
            return FirebaseFirestore.getInstance().collection(TeacherService.TEACHER_COLLECTION)
                .document(UserAuthUtils.getUserFirebaseUid()).getDataFlow { documentSnapshot ->
                    documentSnapshot?.toObject(Teacher::class.java)
                }
        }

        private fun getCurrentStudentFromFirebase(): Flow<Student?> {
            return FirebaseFirestore.getInstance().collection(StudentService.STUDENT_COLLECTION)
                .document(UserAuthUtils.getUserFirebaseUid()).getDataFlow { documentSnapshot ->
                    documentSnapshot?.toObject(Student::class.java)
                }
        }
    }


    lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate() {
        super.onCreate()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }


}