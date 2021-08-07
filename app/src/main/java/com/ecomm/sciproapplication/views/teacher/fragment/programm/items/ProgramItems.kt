package com.ecomm.sciproapplication.views.teacher.fragment.programm.items

import com.ecomm.sciproapplication.R
import com.ecomm.sciproapplication.entities.programElement.ProgramEntity
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.row_item_programm.view.*

class ProgramItem(val program:ProgramEntity):Item(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            this.program_title.text  = program.programTitle
            this.id_programm_detail.text  = program.programContent
            this.id_number_of_course.text = program.numberOfPart.toString()
        }
    }

    override fun getLayout()= R.layout.row_item_programm

}