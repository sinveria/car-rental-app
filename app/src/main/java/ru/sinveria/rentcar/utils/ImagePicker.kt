// utils/ImagePicker.kt
package ru.sinveria.rentcar.utils

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import java.io.File

class ImagePicker {

    @Composable
    fun rememberCameraLauncher(
        cameraImageUri: MutableState<Uri?>,
        onImageCaptured: (Uri?) -> Unit
    ) = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            onImageCaptured(cameraImageUri.value)
        } else {
            onImageCaptured(null)
        }
    }

    @Composable
    fun rememberGalleryLauncher(
        onImageSelected: (Uri?) -> Unit
    ) = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        onImageSelected(uri)
    }
}

fun createImageUri(context: Context): Uri {
    return try {
        val contentResolver = context.contentResolver
        val values = android.content.ContentValues().apply {
            put(android.provider.MediaStore.Images.Media.TITLE, "RentCar_${System.currentTimeMillis()}")
            put(android.provider.MediaStore.Images.Media.DESCRIPTION, "Image from RentCar app")
        }
        contentResolver.insert(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
    } catch (e: Exception) {
        val file = File.createTempFile(
            "RentCar_${System.currentTimeMillis()}",
            ".jpg",
            context.externalCacheDir
        )
        androidx.core.content.FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }
}