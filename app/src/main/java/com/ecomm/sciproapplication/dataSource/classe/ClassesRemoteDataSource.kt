package com.ecomm.sciproapplication.dataSource.classe

import com.ecomm.sciproapplication.dataSource.BaseDataSource
import javax.inject.Inject

class ClassesRemoteDataSource @Inject constructor(private val classeService: ClasseService) :
    BaseDataSource() {

    suspend fun getAllClasses(schoolId: String)= getResultSuspend {
        classeService.getAllClasses(schoolId)
    }
}