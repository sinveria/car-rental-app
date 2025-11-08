package ru.sinveria.rentcar.data.repository

import android.content.Context
import android.net.Uri
import ru.sinveria.rentcar.data.local.dao.CarDao
import ru.sinveria.rentcar.data.local.entity.CarEntity
import ru.sinveria.rentcar.domain.repository.CarRepository
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CarRepositoryImpl @Inject constructor(
    private val carDao: CarDao
) : CarRepository {

    override suspend fun insertCar(car: CarEntity): Long {
        return try {
            carDao.insertCar(car)
        } catch (e: Exception) {
            -1L
        }
    }

    override suspend fun getCarsByUserId(userId: String): List<CarEntity> {
        return carDao.getCarsByUserId(userId)
    }

    override suspend fun getAllCars(): List<CarEntity> {
        return try {
            carDao.getAllCars()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getCarById(carId: Long): CarEntity? {
        return try {
            carDao.getCarById(carId)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun deleteCar(carId: Long) {
        try {
            carDao.deleteCar(carId)
        } catch (e: Exception) {
        }
    }

    override suspend fun deleteCarsWithContentUris(): Int {
        val allCars = getAllCars()
        var deletedCount = 0

        for (car in allCars) {
            if (car.photoUris.isNotBlank() && car.photoUris.contains("content://")) {
                deleteCar(car.id)
                deletedCount++
            }
        }

        return deletedCount
    }

    override suspend fun updateCar(car: CarEntity) {
        try {
            carDao.updateCar(car)
        } catch (e: Exception) {
        }
    }

    override suspend fun migrateCarPhotosToFileUris(context: Context): Int {
        val allCars = getAllCars()
        var updatedCount = 0

        if (allCars.isEmpty()) {
            return 0
        }

        for (car in allCars) {
            val needsMigration = car.photoUris.isNotBlank() && car.photoUris.contains("content://")

            if (needsMigration) {
                val migratedUris = migratePhotosToFileUris(context, car.photoUris)

                if (migratedUris.isNotBlank() && migratedUris != car.photoUris) {
                    val updatedCar = car.copy(photoUris = migratedUris)
                    updateCar(updatedCar)
                    updatedCount++
                }
            }
        }

        return updatedCount
    }

    private suspend fun migratePhotosToFileUris(context: Context, photoUris: String): String {
        val urisList = photoUris.split(",").map { it.trim() }
        val permanentUris = mutableListOf<String>()

        val photosDir = File(context.filesDir, "car_photos")
        if (!photosDir.exists()) {
            photosDir.mkdirs()
        }

        for (uriString in urisList) {
            try {
                if (uriString.startsWith("content://")) {
                    val uri = Uri.parse(uriString)
                    val inputStream = context.contentResolver.openInputStream(uri)

                    if (inputStream != null) {
                        val fileName = "migrated_${System.currentTimeMillis()}_${UUID.randomUUID()}.jpg"
                        val outputFile = File(photosDir, fileName)

                        inputStream.use { input ->
                            outputFile.outputStream().use { output ->
                                input.copyTo(output)
                            }
                        }

                        val fileUri = Uri.fromFile(outputFile).toString()
                        permanentUris.add(fileUri)
                    } else {
                        permanentUris.add(uriString)
                    }
                } else {
                    permanentUris.add(uriString)
                }
            } catch (e: Exception) {
                permanentUris.add(uriString)
            }
        }

        return permanentUris.joinToString(",")
    }
}