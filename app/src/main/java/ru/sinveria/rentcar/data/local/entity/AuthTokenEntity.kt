package ru.sinveria.rentcar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth_tokens")
data class AuthTokenEntity(
    @PrimaryKey
    val userId: String,
    val token: String,
    val refreshToken: String? = null,
    val expiresAt: Long,
    val createdAt: Long = System.currentTimeMillis()
)