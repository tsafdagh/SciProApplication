package com.ecomm.sciproapplication.dataSource.schools

import com.ecomm.sciproapplication.dataSource.BaseDataSource
import com.ecomm.sciproapplication.entities.school.School
import com.ecomm.sciproapplication.utils.DataState
import javax.inject.Inject

class SchoolRemoteDataSource @Inject constructor(private val schoolService: SchoolService) :
    BaseDataSource() {

    suspend fun getAllSchools(): DataState<List<School>> = getResultSuspend {
        schoolService.getAllSchools()
    }

    suspend fun getALLSchoolWhereCheacherIn(cheacherId: String) = getResultSuspend {
        schoolService.getALLSchoolWhereCheacherIn(cheacherId = cheacherId)
    }
}