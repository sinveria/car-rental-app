package ru.sinveria.rentcar.domain.usecase

import ru.sinveria.rentcar.domain.repository.CarRepository
import ru.sinveria.rentcar.data.local.entity.CarEntity
import javax.inject.Inject

class SaveCarUseCase @Inject constructor(
    private val carRepository: CarRepository
) {
    suspend operator fun invoke(car: CarEntity) {
        carRepository.insertCar(car)
    }
}