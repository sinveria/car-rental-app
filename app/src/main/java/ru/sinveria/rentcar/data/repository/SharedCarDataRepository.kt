package ru.sinveria.rentcar.data.repository

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.sinveria.rentcar.data.local.entity.CarEntity
import ru.sinveria.rentcar.domain.repository.CarRepository
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedCarDataRepository @Inject constructor(
    private val carRepository: CarRepository
) {
    private val _addCarData = MutableStateFlow(AddCarData())
    val addCarData = _addCarData.asStateFlow()

    private val _permanentPhotoUris = MutableStateFlow<List<String>>(emptyList())
    val permanentPhotoUris = _permanentPhotoUris.asStateFlow()

    fun updateStepOne(address: String) {
        _addCarData.update { it.copy(address = address) }
    }

    fun updateStepTwo(
        year: Int,
        brand: String,
        model: String,
        transmission: String,
        mileage: Int,
        description: String
    ) {
        _addCarData.update {
            it.copy(
                year = year,
                brand = brand,
                model = model,
                transmission = transmission,
                mileage = mileage,
                description = description
            )
        }
    }

    fun updatePhotos(photoUris: String) {
        val urisList = if (photoUris.isNotBlank()) {
            photoUris.split(",").map { it.trim() }
        } else {
            emptyList()
        }

        _permanentPhotoUris.value = urisList
        _addCarData.update { it.copy(photoUris = photoUris) }
    }

    suspend fun saveCarToDatabase(context: Context): Long {
        val data = _addCarData.value

        if (!isDataComplete(data)) {
            return -1L
        }

        return try {
            val processedPhotoUris = if (data.photoUris.isNotBlank()) {
                processAndCopyPhotos(context, data.photoUris)
            } else {
                ""
            }

            val carEntity = CarEntity(
                id = 0L,
                userId = "",
                address = data.address,
                year = data.year,
                brand = data.brand,
                model = data.model,
                transmission = data.transmission,
                mileage = data.mileage,
                description = data.description,
                photoUris = processedPhotoUris,
                isAvailable = true,
                createdAt = System.currentTimeMillis()
            )

            val carId = carRepository.insertCar(carEntity)

            if (carId > 0) {
                clearData()
            }
            carId
        } catch (e: Exception) {
            -1L
        }
    }

    private suspend fun processAndCopyPhotos(context: Context, photoUris: String): String {
        val urisList = photoUris.split(",").map { it.trim() }
        val permanentUris = mutableListOf<String>()

        val photosDir = File(context.filesDir, "car_photos")
        if (!photosDir.exists()) {
            photosDir.mkdirs()
        }

        for (uriString in urisList) {
            try {
                val uri = Uri.parse(uriString)
                val inputStream = context.contentResolver.openInputStream(uri)

                if (inputStream != null) {
                    val fileName = "car_photo_${System.currentTimeMillis()}_${UUID.randomUUID()}.jpg"
                    val outputFile = File(photosDir, fileName)

                    inputStream.use { input ->
                        outputFile.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }

                    val fileUri = Uri.fromFile(outputFile).toString()
                    permanentUris.add(fileUri)
                }
            } catch (e: Exception) {
                throw Exception("Failed to process photos: ${e.message}")
            }
        }

        return permanentUris.joinToString(",")
    }

    suspend fun copyPhotosToAppStorage(context: Context, originalUris: List<String>): List<String> {
        val permanentUris = mutableListOf<String>()

        val photosDir = File(context.filesDir, "car_photos")
        if (!photosDir.exists()) {
            photosDir.mkdirs()
        }

        for (uriString in originalUris) {
            try {
                val uri = Uri.parse(uriString)
                val inputStream = context.contentResolver.openInputStream(uri)

                if (inputStream == null) {
                    continue
                }

                val fileName = "car_photo_${System.currentTimeMillis()}_${UUID.randomUUID()}.jpg"
                val outputFile = File(photosDir, fileName)

                inputStream.use { input ->
                    outputFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                val fileUri = Uri.fromFile(outputFile).toString()
                permanentUris.add(fileUri)

            } catch (e: Exception) {
            }
        }

        _permanentPhotoUris.value = permanentUris
        val photoUrisString = permanentUris.joinToString(",")
        _addCarData.update { it.copy(photoUris = photoUrisString) }

        return permanentUris
    }

    suspend fun processAndStorePhotos(context: Context, photoUris: String) {
        val urisList = if (photoUris.isNotBlank()) {
            photoUris.split(",").map { it.trim() }
        } else {
            emptyList()
        }

        if (urisList.isNotEmpty()) {
            copyPhotosToAppStorage(context, urisList)
        }
    }

    private fun isDataComplete(data: AddCarData): Boolean {
        return data.address.isNotBlank() &&
                data.year > 0 &&
                data.brand.isNotBlank() &&
                data.model.isNotBlank() &&
                data.transmission.isNotBlank() &&
                data.mileage >= 0 &&
                data.description.isNotBlank() &&
                data.photoUris.isNotBlank()
    }

    fun clearData() {
        _addCarData.update { AddCarData() }
        _permanentPhotoUris.value = emptyList()
    }

    data class AddCarData(
        val address: String = "",
        val year: Int = 0,
        val brand: String = "",
        val model: String = "",
        val transmission: String = "",
        val mileage: Int = 0,
        val description: String = "",
        val photoUris: String = ""
    )
}