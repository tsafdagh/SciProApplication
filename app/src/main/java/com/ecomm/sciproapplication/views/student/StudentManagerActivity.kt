package com.ecomm.sciproapplication.views.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ecomm.sciproapplication.R
import com.ecomm.sciproapplication.base.SciProApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class StudentManagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_manager)

        SciProApplication.loadCurrentStudent()
    }
}