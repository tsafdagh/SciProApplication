package com.ecomm.sciproapplication.dataSource.Teacher

import com.ecomm.sciproapplication.dataSource.BaseDataSource
import com.ecomm.sciproapplication.entities.cheacher.Teacher
import javax.inject.Inject

class TeacherRemoteDataSource @Inject constructor(private val service: TeacherService) :
    BaseDataSource() {

    suspend fun saveTeacherAccountData(
        teacher: Teacher
    ) = getResultSuspend { service.saveTeacherAccountData(teacher) }

    suspend fun getTeacherById(teacherId: String)= getResultSuspend {
        service.getTeacherById(teacherId)
    }
}