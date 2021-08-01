package com.ecomm.sciproapplication.repository.Teacher

import com.ecomm.sciproapplication.dataSource.Teacher.TeacherRemoteDataSource
import com.ecomm.sciproapplication.entities.cheacher.Teacher
import javax.inject.Inject

class TeacherRepository @Inject constructor(private val dataSource: TeacherRemoteDataSource) {

    suspend fun saveTeacherAccountData(
        teacher: Teacher
    ) = dataSource.saveTeacherAccountData(teacher)

    suspend fun getTeacherById(teacherId:String) = dataSource.getTeacherById(teacherId)
}