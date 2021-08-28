package com.ecomm.sciproapplication.views.teacher.fragment.courses.components

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import com.ecomm.sciproapplication.R
import com.ecomm.sciproapplication.entities.courses.CourseEntity
import java.util.*


@Composable
fun CreateCourseDialogFragment(
    openDialog: Boolean,
    pdfImageUri: Uri?,
    filePickerLauncher: ActivityResultLauncher<String>,
    onDismissRequest: () -> Unit,
    onConfirmationClicked: (courseIntitulate: String) -> Unit,
    onDismissButtonClicked: () -> Unit
) {

    Column {
        if (openDialog) {

            val context = LocalContext.current
            val fileName = pdfImageUri?.let { DocumentFile.fromSingleUri(context, it)?.name } ?: ""
            var courseIntitulate by remember { mutableStateOf("") }

            val textFieldColors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(id = R.color.colorPrimary),
                cursorColor = Color.Black,
                backgroundColor = colorResource(id = R.color.white),
                unfocusedIndicatorColor = colorResource(id = R.color.white),
                unfocusedLabelColor = Color.LightGray,
            )

            AlertDialog(
                onDismissRequest = {
                    onDismissRequest()
                },
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.create_a_course),
                        textAlign = TextAlign.Center
                    )
                },
                text = {

                    Column(modifier = Modifier.fillMaxWidth()) {
                        TextField(
                            value = courseIntitulate,
                            onValueChange = { courseIntitulate = it },
                            label = {
                                Text(
                                    text = stringResource(id = R.string.course_intitulate))
                            },
                            singleLine = true,
                            textStyle = TextStyle(
                                color = colorResource(id = R.color.black)
                            ),
                            modifier = Modifier
                                .padding(vertical = 6.dp)
                                .fillMaxWidth(),
                            colors = textFieldColors,
                        )

                        Row(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.clickable {
                                    filePickerLauncher.launch("application/pdf")
                                },
                                text = stringResource(id = R.string.select_pdf_course)
                            )
                            Text(text = fileName)

                        }
                    }
                },
                confirmButton = {

                    val isFieldOk = (courseIntitulate.isNotEmpty() && pdfImageUri != null)

                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .align(Alignment.Start),
                        onClick = {
                            onConfirmationClicked(courseIntitulate)
                        },
                        enabled = isFieldOk,

                        ) {
                        Text(stringResource(id = R.string.save))
                    }

                },
                dismissButton = {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .align(Alignment.End),
                        onClick = {
                            onDismissButtonClicked()
                        },
                    ) {
                        Text(stringResource(id = R.string.cancel))
                    }
                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}