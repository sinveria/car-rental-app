package ru.sinveria.rentcar.domain.usecase

import ru.sinveria.rentcar.domain.repository.CarRepository
import ru.sinveria.rentcar.data.local.entity.CarEntity
import javax.inject.Inject

class GetCarsUseCase @Inject constructor(
    private val carRepository: CarRepository
) {
    suspend operator fun invoke(userId: String): List<CarEntity> {
        return carRepository.getCarsByUserId(userId)
    }
}