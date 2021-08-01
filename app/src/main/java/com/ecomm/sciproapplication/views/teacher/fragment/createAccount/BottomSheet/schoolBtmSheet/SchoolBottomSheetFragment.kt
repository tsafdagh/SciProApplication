package com.ecomm.sciproapplication.views.teacher.fragment.createAccount.BottomSheet.schoolBtmSheet

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecomm.sciproapplication.R
import com.ecomm.sciproapplication.entities.school.School
import com.ecomm.sciproapplication.utils.DataState
import com.ecomm.sciproapplication.views.teacher.fragment.createAccount.BottomSheet.schoolBtmSheet.items.SchoolItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.school_bottom_sheet_fragment.*

@AndroidEntryPoint
class SchoolBottomSheetFragment(private val onComplete: (School) -> Unit) :
    BottomSheetDialogFragment() {


    private lateinit var viewModel: SchoolBottomSheetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.school_bottom_sheet_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SchoolBottomSheetViewModel::class.java)
        observeViewModel()
        viewModel.getAllSchool()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

    private fun showDataIntoView(data: List<School>?) {

        data?.let {
            val items = data.map { SchoolItem(it) }
            updateRecyceView(items)
        }
    }

    lateinit var schoolSection: Section
    private fun updateRecyceView(items: List<SchoolItem>) {
        id_school_rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = GroupAdapter<ViewHolder>().apply {
                schoolSection = Section(items)
                add(schoolSection)
                setOnItemClickListener { item, view ->
                    if (item is SchoolItem) {
                        onComplete(item.school)
                        dismiss()
                    }
                }
            }
        }
    }

}