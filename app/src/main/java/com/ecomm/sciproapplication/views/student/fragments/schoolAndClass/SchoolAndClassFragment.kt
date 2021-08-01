package com.ecomm.sciproapplication.views.student.fragments.schoolAndClass

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecomm.sciproapplication.R

class SchoolAndClassFragment : Fragment() {

    companion object {
        fun newInstance() = SchoolAndClassFragment()
    }

    private lateinit var viewModel: SchoolAndClassViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.school_and_class_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SchoolAndClassViewModel::class.java)
        // TODO: Use the ViewModel
    }

}