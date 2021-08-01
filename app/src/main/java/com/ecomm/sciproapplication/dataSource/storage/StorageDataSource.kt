package com.ecomm.sciproapplication.dataSource.storage

import android.net.Uri
import com.ecomm.sciproapplication.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StorageDataSource {
    /**
     * Upload file of any type to the serve
     * Note: this function not compress the file befor upload
     * @param sousDossier
     * @param fileName
     * @param fileUri
     */
    suspend fun uploadAnyFile(
        sousDossier: String = "",
        fileName: String,
        fileUri: Uri
    ): Flow<Resource<MutableMap<String, Any>>>

    /**
     * Upload user image
     * premit too upload image profil of the user
     * @param imageUri
     */
    suspend fun uploadUserImage(imageUri: Uri): Flow<Resource<String>>
}