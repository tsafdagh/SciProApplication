package com.ecomm.sciproapplication.views.student

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecomm.sciproapplication.R
import kotlinx.android.synthetic.main.create_student_account_fragment.*

class CreateStudentAccountFragment : Fragment() {

    companion object {
        fun newInstance() = CreateStudentAccountFragment()
    }

    private lateinit var viewModel: CreateStudentAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_student_account_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateStudentAccountViewModel::class.java)

        id_imag_progess.visibility = View.GONE
        id_creation_loading.visibility = View.GONE
    }

}