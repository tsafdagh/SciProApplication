package com.ecomm.sciproapplication.views.student.fragments.classProgramm

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecomm.sciproapplication.R

class ClassProgramFragment : Fragment() {

    companion object {
        fun newInstance() = ClassProgramFragment()
    }

    private lateinit var viewModel: ClassProgramViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.class_program_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClassProgramViewModel::class.java)
        // TODO: Use the ViewModel
    }

}