package ru.sinveria.rentcar.domain.usecase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CopyImageUseCase @Inject constructor(
    private val context: Context
) {
    suspend operator fun invoke(temporaryUri: String): String? {
        return try {
            withContext(Dispatchers.IO) {
                val sourceUri = Uri.parse(temporaryUri)

                val inputStream = context.contentResolver.openInputStream(sourceUri)
                if (inputStream == null) {
                    return@withContext null
                }

                inputStream.use { stream ->
                    val bitmap = BitmapFactory.decodeStream(stream)
                    if (bitmap == null) {
                        return@withContext null
                    }

                    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                    val imageFileName = "JPEG_${timeStamp}_"
                    val storageDir = context.filesDir
                    val imageFile = File.createTempFile(
                        imageFileName,
                        ".jpg",
                        storageDir
                    )

                    FileOutputStream(imageFile).use { out ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                    }

                    imageFile.absolutePath
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}