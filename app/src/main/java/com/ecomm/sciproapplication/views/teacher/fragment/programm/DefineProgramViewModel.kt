package com.ecomm.sciproapplication.views.teacher.fragment.programm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ecomm.sciproapplication.base.BaseViewModel
import com.ecomm.sciproapplication.entities.classes.Classes
import com.ecomm.sciproapplication.entities.programElement.ProgramEntity
import com.ecomm.sciproapplication.repository.calsses.ClassesRepository
import com.ecomm.sciproapplication.repository.program.ProgramRepository
import com.ecomm.sciproapplication.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefineProgramViewModel @Inject constructor(
    private val programRepository: ProgramRepository,
    private val classesRepository: ClassesRepository,
    val app: Application
) : BaseViewModel(app) {


    var classId =""
    var schoolId =""
    val allProgram = mutableListOf<ProgramEntity>()


    val saveProgramObserver = MutableLiveData<ProgramEntity>()
    fun saveProgram(programEntity: ProgramEntity, classId:String) = viewModelScope.launch {
        programRepository.saveProgram(programEntity, classId).let {
           when(it){
               is DataState.Success ->{
                   saveProgramObserver.postValue(programEntity)
               }
               is DataState.Failure ->{}
           }
        }
    }

   suspend fun loadAllProgram() = programRepository.getProgramOfSchool(schoolId = schoolId, classeId = classId)


    val allClassesLiveData = MutableLiveData<DataState<List<Classes>>>()

    fun loadclassOfSchool(schoolId:String) = viewModelScope.launch {
        classesRepository.getAllClasses(schoolId =schoolId ).let { classData->
         allClassesLiveData.postValue(classData)
        }
    }

}