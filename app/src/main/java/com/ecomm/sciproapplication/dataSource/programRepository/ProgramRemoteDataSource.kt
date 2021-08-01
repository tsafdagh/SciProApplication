package com.ecomm.sciproapplication.dataSource.programRepository

import com.ecomm.sciproapplication.dataSource.BaseDataSource
import com.ecomm.sciproapplication.entities.programElement.ProgramEntity
import javax.inject.Inject

class ProgramRemoteDataSource @Inject constructor(private val programService: ProgramService) :
    BaseDataSource() {

    suspend fun saveProgram(
        programEntity: ProgramEntity,
        classeId: String
    ) = getResultSuspend {
        programService.saveProgram(programEntity, classeId)
    }

    suspend fun getProgramOfSchool(
        schoolId: String,
        classeId: String
    ) = getResultSuspend {
        programService.getAllProgramOfSchool(schoolId, classeId)
    }
}