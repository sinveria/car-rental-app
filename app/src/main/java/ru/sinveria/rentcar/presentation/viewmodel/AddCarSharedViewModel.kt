package ru.sinveria.rentcar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sinveria.rentcar.domain.usecase.SaveCarUseCase
import ru.sinveria.rentcar.domain.usecase.GetCarsUseCase
import ru.sinveria.rentcar.data.local.entity.CarEntity
import ru.sinveria.rentcar.data.repository.SharedCarDataRepository
import javax.inject.Inject

@HiltViewModel
class AddCarSharedViewModel @Inject constructor(
    private val saveCarUseCase: SaveCarUseCase,
    private val getCarsUseCase: GetCarsUseCase,
    private val sharedCarDataRepository: SharedCarDataRepository
) : ViewModel() {

    private val _cars = MutableStateFlow<List<CarEntity>>(emptyList())
    val cars = _cars.asStateFlow()
    val addCarData = sharedCarDataRepository.addCarData

    fun updateStepOne(address: String) {
        sharedCarDataRepository.updateStepOne(address)
    }

    fun updateStepTwo(
        year: Int,
        brand: String,
        model: String,
        transmission: String,
        mileage: Int,
        description: String
    ) {
        sharedCarDataRepository.updateStepTwo(
            year, brand, model, transmission, mileage, description
        )
    }

    suspend fun saveCar(userId: String) {
        val data = addCarData.value

        require(data.address.isNotBlank()) { "Address is required" }
        require(data.brand.isNotBlank()) { "Brand is required" }
        require(data.model.isNotBlank()) { "Model is required" }
        require(data.transmission.isNotBlank()) { "Transmission is required" }
        require(data.mileage > 0) { "Mileage must be positive" }
        require(data.year in 1900..2025) { "Invalid year" }

        val car = CarEntity(
            id = 0,
            userId = userId,
            address = data.address,
            year = data.year,
            brand = data.brand,
            model = data.model,
            transmission = data.transmission,
            mileage = data.mileage,
            description = data.description,
            photoUris = data.photoUris
        )

        saveCarUseCase(car)
    }

    fun loadCars(userId: String) {
        viewModelScope.launch {
            try {
                _cars.value = getCarsUseCase(userId)
            } catch (e: Exception) {

            }
        }
    }

    data class AddCarData(
        val address: String = "",
        val year: Int = 0,
        val brand: String = "",
        val model: String = "",
        val transmission: String = "",
        val mileage: Int = 0,
        val description: String = "",
        val photoUris: String = ""
    )
}