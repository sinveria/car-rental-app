package ru.sinveria.rentcar.domain.usecase

import ru.sinveria.rentcar.domain.repository.AuthRepository
import ru.sinveria.rentcar.data.local.entity.UserEntity
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): UserEntity? {
        return authRepository.getCurrentUser()
    }
}