package com.ecomm.sciproapplication.views.teacher.fragment.createAccount.BottomSheet.slassesBtmSheet

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecomm.sciproapplication.R
import com.ecomm.sciproapplication.entities.classes.Classes
import com.ecomm.sciproapplication.utils.DataState
import com.ecomm.sciproapplication.views.teacher.fragment.createAccount.BottomSheet.slassesBtmSheet.items.ClassesItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.school_bottom_sheet_fragment.*

@AndroidEntryPoint
class ClasseBottomSheetFragment (private val schoolId:String, private val onComplete: (Classes) -> Unit) :
    BottomSheetDialogFragment() {


    private val viewModel by viewModels<ClasseBottomSheetViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.classes_bottom_sheet_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
        viewModel.getAllSchool(schoolId = schoolId)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun observeViewModel() {
        viewModel.schoolDataState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Loading -> {

                }

                is DataState.Success -> {
                    showDataIntoView(it.data)
                }

                is DataState.Failure -> {

                }
            }
        })
    }

    private fun showDataIntoView(data: List<Classes>?) {

        data?.let {
            val items = data.map { ClassesItem(it) }
            updateRecyceView(items)
        }
    }

    lateinit var schoolSection: Section
    private fun updateRecyceView(items: List<ClassesItem>) {
        id_school_rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = GroupAdapter<ViewHolder>().apply {
                schoolSection = Section(items)
                add(schoolSection)
                setOnItemClickListener { item, view ->
                    if (item is ClassesItem) {
                        onComplete(item.classes)
                        dismiss()
                    }
                }
            }
        }
    }

}