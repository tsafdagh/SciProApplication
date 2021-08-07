package com.ecomm.sciproapplication.views.teacher.fragment.courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ecomm.sciproapplication.entities.courses.CourseEntity
import com.ecomm.sciproapplication.entities.programElement.ProgramEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(): ViewModel() {

    lateinit var schoolId:String
    lateinit var classId:String
    lateinit var programEntity: ProgramEntity

    private val _courses = MutableLiveData<List<CourseEntity>>()
    val courses:LiveData<List<CourseEntity>> =_courses

    fun getCoursesOfChapter(schoolId:String, classId:String, programid:String){

    }
}