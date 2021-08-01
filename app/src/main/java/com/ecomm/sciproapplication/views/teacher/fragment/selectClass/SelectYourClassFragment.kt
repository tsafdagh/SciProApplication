package com.ecomm.sciproapplication.views.teacher.fragment.selectClass

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.ecomm.sciproapplication.R
import com.ecomm.sciproapplication.entities.classes.Classes
import com.ecomm.sciproapplication.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_cheacher_account_fragment.*
import kotlinx.android.synthetic.main.create_cheacher_account_fragment.id_selected_scholls
import kotlinx.android.synthetic.main.select_your_class_fragment.*

@AndroidEntryPoint
class SelectYourClassFragment : Fragment() {

    companion object {
        fun newInstance() = SelectYourClassFragment()
    }

    private lateinit var viewModel: SelectYourClassViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.select_your_class_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(SelectYourClassViewModel::class.java)

        observeViewModel()

        arguments?.let {
            viewModel.schoolId = SelectYourClassFragmentArgs.fromBundle(it).schoolId
            viewModel.getClassOfSchool(schoolId = viewModel.schoolId)
        }
    }


    private fun observeViewModel() {
        viewModel.classesObserver.observe(viewLifecycleOwner, Observer { classDataState ->
            when (classDataState) {
                is DataState.Loading -> {

                }

                is DataState.Success -> {
                    showDataIntoView(classDataState.data)
                }

                is DataState.Failure -> {

                }
            }
        })
    }

    private fun showDataIntoView(data: List<Classes>?) {
        data?.let {
            val arrayAdapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                data.map { it.abreviation })

            id_class_of_school.adapter = arrayAdapter


            id_class_of_school.setOnItemClickListener { parent, view, position, id ->

                val claasId = arrayAdapter.getItem(position)

                claasId?.let {

                    val action =
                        SelectYourClassFragmentDirections.actionSelectYourClassFragmentToDefineProgramFragment(
                            viewModel.schoolId,
                            claasId
                        )
                    Navigation.findNavController(requireView()).navigate(action)
                }
            }
        }

    }

}