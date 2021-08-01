package com.ecomm.sciproapplication.views.teacher.fragment.createAccount.BottomSheet.slassesBtmSheet

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ecomm.sciproapplication.base.BaseViewModel
import com.ecomm.sciproapplication.entities.classes.Classes
import com.ecomm.sciproapplication.repository.calsses.ClassesRepository
import com.ecomm.sciproapplication.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClasseBottomSheetViewModel @Inject constructor(private val classesRepository: ClassesRepository, val app: Application) :
    BaseViewModel(app) {

    val schoolDataState = MutableLiveData<DataState<List<Classes>>>()

    fun getAllSchool(schoolId:String) = viewModelScope.launch {
        classesRepository.getAllClasses(schoolId).let { schoolResult ->
            schoolDataState.postValue(schoolResult)
        }
    }
}