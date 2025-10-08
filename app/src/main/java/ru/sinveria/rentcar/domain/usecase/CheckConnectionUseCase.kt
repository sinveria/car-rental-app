package ru.sinveria.rentcar.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.sinveria.rentcar.domain.repository.ConnectionRepository
import javax.inject.Inject

class CheckConnectionUseCase @Inject constructor(
    private val connectionRepository: ConnectionRepository
) {
    operator fun invoke(): Flow<Boolean> = connectionRepository.isConnected

    suspend fun checkInstantConnection(): Boolean = connectionRepository.checkInstantConnection()
}