package com.ecomm.sciproapplication.dataSource.storage


import android.net.Uri
import com.ecomm.sciproapplication.dataSource.BaseDataSource
import com.ecomm.sciproapplication.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

private const val TAG = "CommonUploadFileDataSou"

@ExperimentalCoroutinesApi
class StorageDataSourceImpl @Inject constructor(
    private val storageService: StorageService
) : StorageDataSource, BaseDataSource() {

    /**
     * Upload file of any type to the serve
     * Note: this function not compress the file befor upload
     * @param sousDossierdd
     * @param fileName
     * @param fileUri
     */
    override suspend fun uploadAnyFile(
        sousDossier: String,
        fileName: String,
        fileUri: Uri
    ) = getFirebaseResult { storageService.uploadAnyFile(sousDossier, fileName, fileUri) }


    /**
     * Upload user image
     * premit too upload image profil of the user
     * @param imageUri
     */
    override suspend fun uploadUserImage(imageUri: Uri): Flow<Resource<String>> =
        getFirebaseResult { storageService.uploadUserImage(imageUri) }

}