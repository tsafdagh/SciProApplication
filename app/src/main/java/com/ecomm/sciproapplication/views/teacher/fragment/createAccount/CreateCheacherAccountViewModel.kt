package com.ecomm.sciproapplication.views.teacher.fragment.createAccount

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ecomm.sciproapplication.base.BaseViewModel
import com.ecomm.sciproapplication.dataSource.storage.DOWNLOAD_FILE_URL
import com.ecomm.sciproapplication.entities.cheacher.Teacher
import com.ecomm.sciproapplication.entities.classes.Classes
import com.ecomm.sciproapplication.entities.school.School
import com.ecomm.sciproapplication.repository.Teacher.TeacherRepository
import com.ecomm.sciproapplication.repository.storage.StorageRepository
import com.ecomm.sciproapplication.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

private const val TAG = "CreateCheacherAccountVi"

@HiltViewModel
@ExperimentalCoroutinesApi
class CreateCheacherAccountViewModel
@Inject constructor (
    private val storageRepository: StorageRepository,
    private val teacherRepository: TeacherRepository,
    val app: Application
) : BaseViewModel(app) {


    val schoolList =mutableListOf <School>()
    val classList = mutableListOf<Classes>()
    var imageUrl =""
    var name =""
    var firstName =""
    var email =""
    var registrationNumber =""

    fun uploadUserImage(imageUri: Uri, addImageToListOfImages: (url: String) -> Unit) {
        val sousDossier = "Cheacher_Images"
        val fileName = Date().toString()
        launch {
            val compressedUri = compressImageFIle(imageUri, getApplication())

            Log.d(TAG, "uploadUserImage: Image compression started")
            storageRepository.uploadAnyFile(sousDossier, fileName, compressedUri)
                .onEach { dataStateResult ->

                    when (dataStateResult.status) {
                        Resource.Status.LOADING -> {
                            Log.d(TAG, "uploadUserImage: Image progress")
                        }

                        Resource.Status.SUCCESS -> {
                            printLogD("UPLOADING IMAGES", "uploadUserImage: Image successfull")

                            dataStateResult.data?.let {

                                val imageUrl = dataStateResult.data[DOWNLOAD_FILE_URL].toString()
                                //val storageRef = dataStateResult.data[FILE_LOCATION].toString()
                                addImageToListOfImages(imageUrl)
                            }
                        }

                        Resource.Status.ERROR -> {
                            printLogD(
                                "UPLOADING IMAGES",
                                "uploadUserImage: Image failure:${dataStateResult.message}"
                            )
                        }
                    }
                }.launchIn(viewModelScope)

        }


        //we begin to compres an image
/*        compressImageFIle(imageUri, getApplication(), onFinish = { compressedUri ->
            printLogD(TAG, "uploadUserImage: Image compression started")

            storageRepository.uploadUserImage(imageUri).onEach {

                printLogD(TAG, "uploadUserImage: Image compression finished")
                _imageUploadProcessLiveData.value = it
            }.launchIn(viewModelScope)
        })*/
    }

    val teacherCreationObserver = MutableLiveData<DataState<Boolean>>()
    fun createTeacher() {
        val teacher = Teacher(
            uid =UserAuthUtils.getUserFirebaseUid(),
            authPhoneNumber = UserAuthUtils.getUserPhone(),
            matricule =registrationNumber,
            name = name,
            firstName =firstName,
            email = email,
            createdAt = Date(),
            schoolsIds =schoolList.map { it.schoolId },
            classesIds = classList.map { it.name },
            matiereIds = arrayListOf()
        )

        viewModelScope.launch {
            teacherRepository.saveTeacherAccountData(teacher = teacher).let {
                teacherCreationObserver.postValue(it)
            }
        }
    }

}