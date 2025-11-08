package ru.sinveria.rentcar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class CarEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val userId: String = "",
    val address: String,
    val year: Int,
    val brand: String,
    val model: String,
    val transmission: String,
    val mileage: Int,
    val description: String,
    val photoUris: String,
    val isAvailable: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)