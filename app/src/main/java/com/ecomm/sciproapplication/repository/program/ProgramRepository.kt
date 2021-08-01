package com.ecomm.sciproapplication.repository.program

import com.ecomm.sciproapplication.dataSource.programRepository.ProgramRemoteDataSource
import com.ecomm.sciproapplication.entities.programElement.ProgramEntity
import javax.inject.Inject

class ProgramRepository @Inject constructor(private val programRemoteDataSource: ProgramRemoteDataSource) {

    suspend fun saveProgram(
        programEntity: ProgramEntity,
        classeId: String
    ) = programRemoteDataSource.saveProgram(programEntity, classeId)

    suspend fun getProgramOfSchool(
        schoolId: String,
        classeId: String
    ) = programRemoteDataSource.getProgramOfSchool(schoolId, classeId)
}