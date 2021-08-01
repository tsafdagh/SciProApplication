package com.ecomm.sciproapplication.views.teacher.fragment.createAccount.BottomSheet.schoolBtmSheet

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ecomm.sciproapplication.base.BaseViewModel
import com.ecomm.sciproapplication.entities.school.School
import com.ecomm.sciproapplication.repository.school.SchoolRepository
import com.ecomm.sciproapplication.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolBottomSheetViewModel @Inject constructor(private val schoolRepository: SchoolRepository, val app:Application) :
    BaseViewModel(app) {

    val schoolDataState = MutableLiveData<DataState<List<School>>>()

    fun getAllSchool() = viewModelScope.launch {
        schoolRepository.getAllSchools().let { schoolResult ->
            schoolDataState.postValue(schoolResult)
        }
    }
}