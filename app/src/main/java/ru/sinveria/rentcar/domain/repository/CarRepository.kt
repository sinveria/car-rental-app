package ru.sinveria.rentcar.domain.repository

import android.content.Context
import ru.sinveria.rentcar.data.local.entity.CarEntity

interface CarRepository {
    suspend fun insertCar(car: CarEntity): Long
    suspend fun getCarsByUserId(userId: String): List<CarEntity>
    suspend fun getAllCars(): List<CarEntity>
    suspend fun getCarById(carId: Long): CarEntity?
    suspend fun deleteCar(carId: Long)
    suspend fun updateCar(car: CarEntity)
    suspend fun migrateCarPhotosToFileUris(context: Context): Int

    suspend fun deleteCarsWithContentUris(): Int
}