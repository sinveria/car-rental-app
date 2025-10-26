package ru.sinveria.rentcar.domain.usecase

import ru.sinveria.rentcar.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): LoginResult {
        return authRepository.login(email, password)
    }
}