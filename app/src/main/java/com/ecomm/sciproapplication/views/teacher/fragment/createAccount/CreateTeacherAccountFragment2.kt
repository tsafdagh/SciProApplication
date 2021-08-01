package com.ecomm.sciproapplication.views.teacher.fragment.createAccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.ecomm.sciproapplication.R
import com.ecomm.sciproapplication.entities.classes.Classes
import com.ecomm.sciproapplication.utils.DataState
import com.ecomm.sciproapplication.views.teacher.fragment.createAccount.BottomSheet.slassesBtmSheet.ClasseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_cheacher_account_fragment.*
import kotlinx.android.synthetic.main.fragment_create_teacher_account2.*
import kotlinx.android.synthetic.main.fragment_create_teacher_account2.id_next_button
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CreateTeacherAccountFragment2 : Fragment() {


    val viewModel by activityViewModels<CreateCheacherAccountViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_teacher_account2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        consfigureOnCLickListener()
    }

    private fun consfigureOnCLickListener() {
        id_select_classes.setOnClickListener {
            val bottomSheet = ClasseBottomSheetFragment(
                schoolId = viewModel.schoolList.first().schoolId,
                onComplete = {
                    viewModel.classList.add(it)
                    viewModel.classList.distinct()
                    showSelectedClsses(viewModel.classList)
                })
            bottomSheet.show(childFragmentManager, "")
        }

        id_next_button.setOnClickListener {
            if (viewModel.classList.isNotEmpty()) {
                createSaveTeacher()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.select_class),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun showSelectedClsses(schoolList: MutableList<Classes>) {
        val arrayAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            schoolList.map { it.name })
        id_selected_classes.adapter = arrayAdapter
    }


    private fun createSaveTeacher() {
        teacher_creation_progress.visibility = View.VISIBLE
        viewModel.createTeacher()
    }

    private fun observeViewModel() {
        viewModel.teacherCreationObserver.observe(viewLifecycleOwner, Observer { teachercreate ->
            when (teachercreate) {
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    teacher_creation_progress.visibility = View.GONE

                    val action = CreateTeacherAccountFragment2Directions.actionCreateTeacherAccountFragment2ToSelectYourClassFragment(viewModel.schoolList.first().schoolId)
                    Navigation.findNavController(requireView()).navigate(action)

                }
                is DataState.Failure -> {
                    teacher_creation_progress.visibility = View.GONE

                }
            }
        })
    }

}