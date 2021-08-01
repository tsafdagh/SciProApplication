package com.ecomm.sciproapplication.repository.calsses

import com.ecomm.sciproapplication.dataSource.classe.ClassesRemoteDataSource
import com.ecomm.sciproapplication.entities.classes.Classes
import com.ecomm.sciproapplication.utils.DataState
import javax.inject.Inject

class ClassesRepository @Inject constructor(private val dataSource: ClassesRemoteDataSource) {

    suspend fun getAllClasses(schoolId: String): DataState<List<Classes>> =
        dataSource.getAllClasses(schoolId = schoolId)
}