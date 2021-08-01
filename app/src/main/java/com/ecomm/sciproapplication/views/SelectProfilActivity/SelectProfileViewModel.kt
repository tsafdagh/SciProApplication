package com.ecomm.sciproapplication.views.SelectProfilActivity

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ecomm.sciproapplication.base.BaseViewModel
import com.ecomm.sciproapplication.entities.cheacher.Teacher
import com.ecomm.sciproapplication.repository.Teacher.TeacherRepository
import com.ecomm.sciproapplication.repository.student.StudentRepository
import com.ecomm.sciproapplication.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectProfileViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository,
    private val studentRepository:StudentRepository,
    val app: Application
) : BaseViewModel(app) {

    val teacherObServer = MutableLiveData<DataState<Teacher>>()

    fun getTeacherById(teacherId:String) = viewModelScope.launch {
        teacherRepository.getTeacherById(teacherId).let {
            teacherObServer.postValue(it)
        }
    }
}