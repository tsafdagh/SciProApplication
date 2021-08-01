package com.ecomm.sciproapplication.repository.storage


import android.net.Uri
import com.ecomm.sciproapplication.dataSource.storage.StorageDataSource
import com.ecomm.sciproapplication.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@ExperimentalCoroutinesApi
class StorageRepository
@Inject constructor(val commonUploadFileDataSource: StorageDataSource) {


    /**
     * Upload file of any type to the serve
     * Note: this function not compress the file befor upload
     * @param sousDossierdd
     * @param fileName
     * @param fileUri
     */
    fun uploadAnyFile(
        sousDossier: String = "",
        fileName: String,
        fileUri: Uri
    ): Flow<Resource<MutableMap<String, Any>>> = flow {

        emit(Resource.loading(null))
        //and then, we upload it to the server
        emitAll(commonUploadFileDataSource.uploadAnyFile(sousDossier, fileName, fileUri))
    }


    /**
     * Upload user image:
     * the goal of this function consist to take image Uri, compress it,
     * upload it to the server and return the imageUrl
     * @param imageUri
     * @return imageUrl:String
     */
    fun uploadUserImage(imageUri: Uri): Flow<Resource<String>> = flow {

        emit(Resource.loading(null))
        //and then, we upload it to the server
        emitAll(commonUploadFileDataSource.uploadUserImage(imageUri))

    }
}
