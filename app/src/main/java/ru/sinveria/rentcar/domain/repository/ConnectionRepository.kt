package ru.sinveria.rentcar.domain.repository

import kotlinx.coroutines.flow.Flow

interface ConnectionRepository {
    val isConnected: Flow<Boolean>
    suspend fun checkInstantConnection(): Boolean
}