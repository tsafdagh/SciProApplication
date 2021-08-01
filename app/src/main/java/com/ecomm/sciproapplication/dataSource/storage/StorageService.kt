package com.ecomm.sciproapplication.dataSource.storage

import android.net.Uri
import com.ecom.ecomm.utils.FirebaseResponseType
import com.ecomm.sciproapplication.utils.cLog
import com.ecomm.sciproapplication.utils.printLogD
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val TAG = "UploadFIleService"
const val DOWNLOAD_FILE_URL = "DOWNLOAD_FILE_URL"
const val FILE_LOCATION = "FILE_LOCATION"

@ExperimentalCoroutinesApi
class StorageService @Inject constructor(
    private val storageRef: StorageReference
) {
    /**
     * Upload file of any type to the serve
     * Note: this function not compress the file befor upload
     * @param sousDossier
     * @param fileName
     * @param fileUri
     */
    fun uploadAnyFile(
        sousDossier: String = "",
        fileName: String,
        fileUri: Uri
    ): Flow<FirebaseResponseType<MutableMap<String, Any>>> = callbackFlow {

        val fileLocation = if (sousDossier.isEmpty()) fileName else "$sousDossier/$fileName"

        val riversRef = storageRef.child(fileLocation)
        val uploadTask = fileUri.let { riversRef.putFile(it) }

        uploadTask.addOnFailureListener {
            printLogD(TAG, "Erreur d'upload ${it.message}")
            try {
                offer(FirebaseResponseType.FirebaseErrorResponse(it))
            } catch (ex: Exception) {
                ex.printStackTrace()
                cLog(ex.message)
                printLogD(TAG, ex.message ?: "")
            }
        }.addOnSuccessListener {
            val urlTask =
                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation riversRef.downloadUrl
                }).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result.toString()

                        val resultMap = mutableMapOf<String, Any>().apply {
                            this[DOWNLOAD_FILE_URL] = downloadUri
                            this[FILE_LOCATION] = fileLocation
                        }

                        try {
                          offer(FirebaseResponseType.FirebaseSuccessResponse(resultMap))
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            cLog(ex.message)
                            printLogD(TAG, ex.message ?: "")
                        }
                    } else {
                        printLogD(TAG, "Erreur d'upload")
                        try {
                            offer(FirebaseResponseType.FirebaseErrorResponse(task.exception))
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            cLog(ex.message)
                            printLogD(TAG, ex.message ?: "")
                        }
                    }
                }
        }

        awaitClose {
            this.close()
        }
    }


    fun uploadUserImage(imageUri: Uri): Flow<FirebaseResponseType<String>> = callbackFlow {


        val riversRef = storageRef.child(
            STORAGE_USER_PROFILS + "/" + (0..20000).random()
        )

        val uploadTask = imageUri.let { riversRef.putFile(it) }

        uploadTask.addOnFailureListener { exeption ->
            try {
                offer(FirebaseResponseType.FirebaseErrorResponse(exeption))
            } catch (ex: Exception) {
                ex.printStackTrace()
                cLog(ex.message)
                printLogD(TAG, ex.message ?: "")
            }
        }.addOnSuccessListener {
            val urlTask =
                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation riversRef.downloadUrl
                }).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result

                        try {
                            offer(FirebaseResponseType.FirebaseSuccessResponse(downloadUri.toString()))
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            cLog(ex.message)
                            printLogD(TAG, ex.message ?: "")
                        }

                    } else {
                        // Handle failures
                        try {
                            offer(FirebaseResponseType.FirebaseErrorResponse())
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            cLog(ex.message)
                            printLogD(TAG, ex.message ?: "")
                        }
                    }

                }
        }

        awaitClose {
            this.close()
        }
    }

    companion object {
        const val STORAGE_USER_PROFILS = "STORAGE_USER_PROFILS"
    }

}