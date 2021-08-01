package com.ecomm.sciproapplication.repository.school

import com.ecomm.sciproapplication.dataSource.schools.SchoolRemoteDataSource
import com.ecomm.sciproapplication.entities.school.School
import com.ecomm.sciproapplication.utils.DataState
import javax.inject.Inject

class SchoolRepository @Inject constructor(private val remoteDataSource: SchoolRemoteDataSource) {

    suspend fun getAllSchools(): DataState<List<School>> = remoteDataSource.getAllSchools()

    suspend fun getALLSchoolWhereCheacherIn(cheacherId: String) =
        remoteDataSource.getALLSchoolWhereCheacherIn(cheacherId = cheacherId)
}