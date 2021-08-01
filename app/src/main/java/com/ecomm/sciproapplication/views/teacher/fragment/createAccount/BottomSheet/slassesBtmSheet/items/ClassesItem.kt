package com.ecomm.sciproapplication.views.teacher.fragment.createAccount.BottomSheet.slassesBtmSheet.items

import com.ecomm.sciproapplication.R
import com.ecomm.sciproapplication.entities.classes.Classes
import com.ecomm.sciproapplication.entities.school.School
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.row_item_school_item.view.*


class ClassesItem(val classes: Classes) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.school_name.text = classes.name
        viewHolder.itemView.id_school_description.text = classes.abreviation
    }

    override fun getLayout() = R.layout.row_item_school_item
}