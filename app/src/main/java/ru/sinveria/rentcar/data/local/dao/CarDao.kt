package ru.sinveria.rentcar.data.local.dao

import androidx.room.*
import ru.sinveria.rentcar.data.local.entity.CarEntity

@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: CarEntity): Long

    @Query("SELECT * FROM cars WHERE userId = :userId ORDER BY id DESC")
    suspend fun getCarsByUserId(userId: String): List<CarEntity>

    @Query("SELECT * FROM cars WHERE id = :carId")
    suspend fun getCarById(carId: Long): CarEntity?

    @Query("DELETE FROM cars WHERE id = :carId")
    suspend fun deleteCar(carId: Long)

    @Query("SELECT * FROM cars ORDER BY id DESC LIMIT 1")
    suspend fun getLastAddedCar(): CarEntity?

    @Query("SELECT * FROM cars ORDER BY createdAt DESC")
    suspend fun getAllCars(): List<CarEntity>

    @Update
    suspend fun updateCar(car: CarEntity)
}