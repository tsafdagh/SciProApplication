package com.ecomm.sciproapplication.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import id.zelory.compressor.Compressor
import java.io.File


suspend fun compressImageFIle(fileUri: Uri, context: Context): Uri {

    var actualImageFile: File? = null
    var compressedImageFile: File? = null
    return try {
        actualImageFile = File(getRealPathFromUri(context, fileUri)!!)
        compressedImageFile = Compressor.compress(context, actualImageFile)
        Uri.fromFile(compressedImageFile)
    } catch (e: Exception) {
        e.printStackTrace()
        fileUri
    }

}

fun getRealPathFromUri(
    context: Context,
    contentUri: Uri?
): String? {
    var cursor: Cursor? = null
    return try {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
        val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        cursor.getString(column_index)
    } finally {
        cursor?.close()
    }
}