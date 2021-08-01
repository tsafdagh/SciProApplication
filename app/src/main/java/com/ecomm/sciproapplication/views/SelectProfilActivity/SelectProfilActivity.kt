package com.ecomm.sciproapplication.views.SelectProfilActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.ecomm.sciproapplication.R
import com.ecomm.sciproapplication.base.SciProApplication
import com.ecomm.sciproapplication.entities.cheacher.Teacher
import com.ecomm.sciproapplication.utils.DataState
import com.ecomm.sciproapplication.utils.UserAuthUtils
import com.ecomm.sciproapplication.views.teacher.TeacherManagerActivity
import com.ecomm.sciproapplication.views.student.StudentManagerActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_select_profil.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SelectProfilActivity : AppCompatActivity() {

    private val selectProfilViewModel by viewModels<SelectProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_profil)

        title = getString(R.string.select_profil)

        observeViewModel()
        selectProfilViewModel.getTeacherById(UserAuthUtils.getUserFirebaseUid())
        id_idstudent_opetion.setOnClickListener {
            val intent = Intent(this, StudentManagerActivity::class.java)
            startActivity(intent)
        }

        id_chearcher_profil.setOnClickListener {
            val intent = Intent(this, TeacherManagerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        selectProfilViewModel.teacherObServer.observe(this, Observer { result ->
            when (result) {
                is DataState.Loading -> {
                    id_progress_bar.visibility =View.GONE
                }
                is DataState.Success -> {
                    id_progress_bar.visibility =View.GONE
                    result.data?.let {
                        SciProApplication.currentTeacher =it

                        startTeacherActivityWithBundle(teacher = it)
                    }
                    id_progress_bar.visibility =View.GONE
                }
                is DataState.Failure -> {
                    result.throwable
                    id_progress_bar.visibility =View.GONE

                    val exceptionMessage = result.throwable?.message

                    exceptionMessage?.let {

                        if(it.contains("Failed to get document because the client is offline.", true)){
                            Toast.makeText(
                                this,
                                getString(R.string.check_internet_connexion),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        })
    }

    private fun startTeacherActivityWithBundle(teacher: Teacher){
        val gson = GsonBuilder().create()
        val intent = Intent(this, TeacherManagerActivity::class.java)
        intent.putExtra("TEACHER", gson.toJson(teacher))
        startActivity(intent)
    }
}