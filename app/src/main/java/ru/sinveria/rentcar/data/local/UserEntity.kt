package ru.sinveria.rentcar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    val password: String,
    val lastName: String,
    val firstName: String,
    val middleName: String,
    val birthDate: String,
    val gender: String,
    val licenseNumber: String,
    val licenseIssueDate: String,
    val licensePhotoPath: String?,
    val passportPhotoPath: String?,
    val profilePhotoPath: String?,
    val createdAt: Long = System.currentTimeMillis()
)