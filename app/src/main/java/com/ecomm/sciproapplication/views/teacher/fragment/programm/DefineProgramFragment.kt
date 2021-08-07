package com.ecomm.sciproapplication.views.teacher.fragment.programm

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecomm.sciproapplication.R
import com.ecomm.sciproapplication.entities.classes.Classes
import com.ecomm.sciproapplication.entities.programElement.ProgramEntity
import com.ecomm.sciproapplication.utils.DataState
import com.ecomm.sciproapplication.views.custum.CustumAlertDialog
import com.ecomm.sciproapplication.views.teacher.fragment.programm.items.ProgramItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.define_program_fragment.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DefineProgramFragment : Fragment() {

    companion object {
        fun newInstance() = DefineProgramFragment()
    }

    val viewModel by viewModels<DefineProgramViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.define_program_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        obServeViewModel()
        arguments?.let {
            viewModel.schoolId = DefineProgramFragmentArgs.fromBundle(it).schoolid
            viewModel.classId = DefineProgramFragmentArgs.fromBundle(it).classId
            loadClass()

        }
        obServeViewModel()
        configureOnCLickListener()
        loadProgramm()
    }

    private fun loadClass() {
        viewModel.loadclassOfSchool(viewModel.schoolId)
    }

    private fun loadProgramm() {
        id_load_program_progresssbar.visibility = View.VISIBLE
        lifecycleScope.launch {
            viewModel.loadAllProgram().let { programDataState ->
                when (programDataState) {

                    is DataState.Loading -> {

                    }
                    is DataState.Success -> {
                        id_load_program_progresssbar.visibility = View.GONE
                        showDataIntoList(programDataState.data as MutableList<ProgramEntity>?)
                    }
                    is DataState.Failure -> {
                        id_load_program_progresssbar.visibility = View.GONE
                        programDataState.throwable?.let {
                            anErrorOccured(it)
                        }
                    }
                }
            }
        }
    }


    private fun showDataIntoList(data: MutableList<ProgramEntity>?) {

        data?.let { chapterlist ->
            val chapters = chapterlist.distinctBy { it.programTitle }

            id_number_ofChapter.text = "${chapters.size} ${getString(R.string.Chapters)}"
            val items = chapters.map { ProgramItem(it) }
            val section = Section(items)
            id_programm_list.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = GroupAdapter<ViewHolder>().apply {
                    add(section)
                    setOnItemClickListener { item, view ->

                        if (item is ProgramItem) {
                            navigateToCoursesView(item.program)
                        }
                    }
                }

            }

        }
    }

    private fun navigateToCoursesView(program: ProgramEntity) {
        val action = DefineProgramFragmentDirections.actionDefineProgramFragmentToCoursesFragment(
            viewModel.schoolId,
            viewModel.classId,
            program
        )
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun anErrorOccured(throwable: Throwable) {
        Toast.makeText(
            requireContext(),
            "An error occured: ${throwable.message}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun obServeViewModel() {
        viewModel.allClassesLiveData.observe(viewLifecycleOwner, Observer { classdataState ->
            when (classdataState) {
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    classdataState.data?.let { classList ->
                        showClassIntoSpinner(classList)
                    }
                }

                is DataState.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.an_error_occurred),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })


        viewModel.saveProgramObserver.observe(viewLifecycleOwner, Observer { programEntity ->
            viewModel.allProgram.add(programEntity)
            loadProgramm()

        })
    }

    private fun showClassIntoSpinner(classList: List<Classes>) {

        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item, classList.map { it.abreviation }
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val text = view.findViewById<View>(android.R.id.text1) as TextView
                text.setTextColor(Color.WHITE)
                return view
            }
        }

        id_classes_sci_spinner.adapter = adapter

        id_classes_sci_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.classId = id_classes_sci_spinner.selectedItem.toString()
                    loadProgramm()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
    }

    private fun configureOnCLickListener() {

        id_add_programm.setOnClickListener {
            CustumAlertDialog.showAddContactDialog(
                layoutInflater = layoutInflater,
                context = requireContext(),
                schoolId = viewModel.schoolId,
                onFinish = {
                    viewModel.saveProgram(it, viewModel.classId)
                })
        }
    }

}