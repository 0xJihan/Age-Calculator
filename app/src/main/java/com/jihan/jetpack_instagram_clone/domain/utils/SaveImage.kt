package com.jihan.jetpack_instagram_clone.domain.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.util.UUID


fun saveImage(context: Context, imageUri: Uri?): String? {
    if (imageUri == null) return null


    val contentResolver: ContentResolver = context.contentResolver
    val imageFile = File(context.filesDir, "${UUID.randomUUID()}.jpeg")

    try {
        contentResolver.openInputStream(imageUri)?.use { inputStream ->

            val bitmap = BitmapFactory.decodeStream(inputStream)
            FileOutputStream(imageFile).use { outputStream ->
                 bitmap.compress(
                    Bitmap.CompressFormat.JPEG, 40, outputStream
                )

                    return imageFile.absolutePath // Return the file path if saved successfully


            }
        }
    } catch (e: Exception) {
        println(e.message)
    }

    println("Failed to save image")
    return null
}


fun deleteImage(filePath: String?): Boolean {
    if (filePath.isNullOrBlank()) return false
    val file = File(filePath)
    return if (file.exists()) {
        file.delete()
    } else {
        false
    }
}


