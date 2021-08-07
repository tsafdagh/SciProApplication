package com.ecomm.sciproapplication.views.teacher.fragment.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ecomm.sciproapplication.R
import com.ecomm.sciproapplication.entities.courses.CourseEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.courses_fragment.*
import java.util.*

@AndroidEntryPoint
class CoursesFragment : Fragment() {

    companion object {
        fun newInstance() = CoursesFragment()
    }


    private val viewModel: CoursesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.courses_fragment, container, false)
    }

    @ExperimentalFoundationApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fun onBackPressed() {
            Navigation.findNavController(requireView()).navigateUp()
        }

        arguments?.let {
            viewModel.programEntity = CoursesFragmentArgs.fromBundle(it).currentProgram
            viewModel.classId = CoursesFragmentArgs.fromBundle(it).classId
            viewModel.schoolId = CoursesFragmentArgs.fromBundle(it).schoolid

            compose_view.setContent {
                MaterialTheme {
                    ShowCourseView(viewModel, onBackPressed = { onBackPressed() })
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun ShowCourseView(viewModel: CoursesViewModel, onBackPressed: () -> Unit) {
    //val courses  = remember{ mutableStateListOf<CourseEntity>()}

    val courses by viewModel.courses.observeAsState()
    viewModel.getCoursesOfChapter(
        viewModel.schoolId,
        viewModel.classId,
        viewModel.programEntity.programId
    )

    Scaffold(
        topBar = {
            CourseTopbar(viewModel, onBackPressed)
        },
        floatingActionButton = {
            CourseFloatingActionButton(addCourseClick = {
                //TODO show view to add course
            })
        },
        content = {

            ShowCoursesContent(courses)
        }

    )
}

@ExperimentalFoundationApi
@Preview
@Composable
fun previewCourseContent() {
    MaterialTheme {
        val courses = mutableListOf<CourseEntity>()
        (0..15).forEach {
            courses.add(createEmptyCourse())
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Programm title",style = TextStyle(color = Color.Red)) },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.ArrowBack, "")
                        }
                    },
                    backgroundColor = Color.White
                )
            },
            floatingActionButton = {
                CourseFloatingActionButton(addCourseClick = {})
            },
            content = {
                ShowCoursesContent(courses)
            }

        )

    }
}

fun createEmptyCourse(): CourseEntity {
    return CourseEntity(
        uid = UUID.randomUUID().toString(),
        pdfUrl = UUID.randomUUID().toString(),
        saveDate = Date(),
        illustrationUrl = UUID.randomUUID().toString(),
        teacherId = "",
        smalContent = "This is a small description of my course read it seriously"
    )
}

@ExperimentalFoundationApi
@Composable
fun ShowCoursesContent(courses: List<CourseEntity>?) {
    Surface(modifier = Modifier.fillMaxSize()) {
        if (!courses.isNullOrEmpty()) {

            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(courses!!) { course ->
                    CourseCardItem(course = course, onCourseClicked = {})
                }
            }

        } else {
            Text(text = stringResource(id = R.string.no_course_avalaible))
        }

    }
}

@Composable
fun CourseCardItem(course: CourseEntity, onCourseClicked: () -> Unit) {

    Card(
        modifier = Modifier
            .wrapContentWidth()
            .height(200.dp)
            .padding(
                top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp
            )
            .clickable(onClick = onCourseClicked),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CourseImg(course.pdfUrl, 72.dp)
            CourseContent(course.smalContent, Alignment.CenterHorizontally)
        }
    }
}

@Composable
fun CourseContent(smalContent: String, centerHorizontally: Alignment.Horizontal) {
    Text(
        text = smalContent,
        style = MaterialTheme.typography.subtitle2,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 3.dp)
    )
}

@Composable
fun CourseImg(pdfUrl: String, imagSize: Dp) {
    Card(
        shape = CircleShape,
        border = BorderStroke(width = 2.dp, color = Color.White),
        modifier = Modifier
            .padding(16.dp)
            .size(imagSize),
        elevation = 4.dp
    ) {
        Image(
            painter = painterResource(id = R.drawable.pdf_illustration2),
            contentDescription = null
        )
    }
}

@Composable
private fun CourseFloatingActionButton(addCourseClick:() ->Unit) {
    FloatingActionButton(onClick = { addCourseClick()},
        backgroundColor = Color.Red,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_add_24),
                contentDescription = null,
                tint = Color.White
            )
        })
}

@Composable
private fun CourseTopbar(viewModel: CoursesViewModel, onBackPressed: () -> Unit) {
    TopAppBar(
        title = { Text(text = viewModel.programEntity.programTitle, style = TextStyle(color = Color.Red)) },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(Icons.Default.ArrowBack, "")
            }
        },
        backgroundColor = Color.White
    )
}
