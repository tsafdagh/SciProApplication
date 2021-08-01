package com.ecomm.sciproapplication.views.teacher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ecomm.sciproapplication.R
import com.ecomm.sciproapplication.base.SciProApplication
import com.ecomm.sciproapplication.entities.cheacher.Teacher
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TeacherManagerActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheacher_manager)
        SciProApplication.loadCurrentTeacher()

        navController = findNavController(R.id.taacherNav_host_fragment)
        val extra = intent.extras
        extra?.let {
            val teacherString = extra.getString("TEACHER")
            teacherString?.let {
                val gson = GsonBuilder().create()
                val teacher = gson.fromJson(it,Teacher::class.java)

                teacher?.let { teach->
                    navigateToTeacherview(teach)
                }
            }
        }
    }

    private fun navigateToTeacherview(teacher: Teacher) {
        val bundle = bundleOf("schoolId" to teacher.schoolsIds.first())
        navController.navigate(R.id.selectYourClassFragment, bundle)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}