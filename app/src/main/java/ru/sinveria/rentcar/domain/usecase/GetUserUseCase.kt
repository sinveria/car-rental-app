package ru.sinveria.rentcar.domain.usecase

import ru.sinveria.rentcar.data.repository.LocalRepositoryImpl
import ru.sinveria.rentcar.data.local.entity.UserEntity
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val localRepository: LocalRepositoryImpl
) {
    suspend operator fun invoke(email: String): UserEntity? {
        return localRepository.getUserByEmail(email)
    }

    suspend fun getLastUser(): UserEntity? {
        val allUsers = localRepository.getAllUsers()
        return allUsers.lastOrNull()
    }
}