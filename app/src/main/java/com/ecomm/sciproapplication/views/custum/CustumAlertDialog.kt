package com.ecomm.sciproapplication.views.custum

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.ecomm.sciproapplication.R
import com.ecomm.sciproapplication.entities.programElement.ProgramEntity
import com.ecomm.sciproapplication.utils.UserAuthUtils
import kotlinx.android.synthetic.main.custom_alert_create_program.view.*
import java.util.*

object CustumAlertDialog {

    fun showAddContactDialog(
        layoutInflater: LayoutInflater,
        context: Context,
        schoolId: String,
        onFinish: (ProgramEntity) -> Unit
    ) {
        val inflater =
            layoutInflater.inflate(R.layout.custom_alert_create_program, null)

        inflater.apply {
            val dialog = android.app.AlertDialog.Builder(context)
                .setView(this)
                .create()

            inflater.id_save_button.setOnClickListener {

                val title = inflater.id_program_title.text.toString()
                val description = inflater.id_program_description.text.toString()

                if (title.isNotEmpty() && description.isNotEmpty()) {
                    val programm = ProgramEntity(
                        programId = UUID.randomUUID().toString(),
                        programTitle = title,
                        programContent = description,
                        saveAt = Date(),
                        schoolId = schoolId,
                        teacherId = UserAuthUtils.getUserPhone()
                    )
                    onFinish(programm)
                    dialog.dismiss()
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.enter_all_fields),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            dialog.setCancelable(true)
            dialog.show()

        }
    }
}