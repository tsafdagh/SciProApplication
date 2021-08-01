package com.ecomm.sciproapplication.views.teacher.fragment.createAccount.BottomSheet.schoolBtmSheet.items

import com.ecomm.sciproapplication.R
import com.ecomm.sciproapplication.entities.school.School
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.row_item_school_item.view.*

class SchoolItem(val school: School) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.school_name.text = school.name
        viewHolder.itemView.id_school_description.text = school.slogan
    }

    override fun getLayout() = R.layout.row_item_school_item
}