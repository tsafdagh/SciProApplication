package com.ecomm.sciproapplication.views.teacher.fragment.selectClass

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
class SelectYourClassViewModel @Inject constructor(
    private val classesRepository: ClassesRepository,
    val app: Application
) : BaseViewModel(app) {


    var schoolId =""
    val classesObserver = MutableLiveData<DataState<List<Classes>>>()

    fun getClassOfSchool(schoolId: String) = viewModelScope.launch {
        classesRepository.getAllClasses(schoolId = schoolId).let {
            classesObserver.postValue(it)
        }
    }
}